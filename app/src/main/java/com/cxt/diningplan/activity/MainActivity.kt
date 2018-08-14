package com.cxt.diningplan.activity

import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Rect
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cxt.diningplan.R
import com.cxt.diningplan.common.CommonConst
import com.cxt.diningplan.customview.OrderListAdapter
import com.cxt.diningplan.entity.Order
import com.cxt.diningplan.enums.OrderStatus
import com.cxt.diningplan.extend.assignSchedulers
import com.cxt.diningplan.extend.toPx
import com.cxt.diningplan.extend.with
import com.cxt.diningplan.mapper.OrderMapper
import com.cxt.diningplan.repository.OrderRepository
import com.cxt.diningplan.repository.UserRepository
import com.cxt.diningplan.extend.showToast
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseEditTextActivity() {

    private var scanFinishAction: ((Order?) -> Unit)? = null
    private val nfcAdapter: NfcAdapter? by lazy { NfcAdapter.getDefaultAdapter(this) }
    private val pendingIntent by lazy { PendingIntent.getActivity(this, 0, Intent(this, this::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0) }
    private val submitAction: (Order) -> Unit = {
        val message = Html.fromHtml(getString(R.string.alert_submit_order_confirm).with(it.name))
        AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok)) { _, _ -> submitOrder(it) }
                .setNeutralButton(getString(R.string.cancel), null)
                .show()
    }

    private val adapter = OrderListAdapter(submitAction)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.inflateMenu(R.menu.menu_logout)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.logout_button -> {
                    AlertDialog.Builder(this)
                            .setMessage(R.string.alert_logout_confirm)
                            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                                UserRepository.clear()
                                moveToLogin()
                            }.setNeutralButton(getString(R.string.cancel), null)
                            .show()
                }
                else -> Unit
            }
            true
        }

        order_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        order_list.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.top = 15.toPx()
                outRect.left = 15.toPx()
                outRect.right = 15.toPx()
                outRect.bottom = when (parent.getChildAdapterPosition(view) == (parent.adapter?.itemCount
                        ?: 0) - 1) {
                    true -> 50.toPx()
                    else -> 0
                }
            }
        })
        order_list.adapter = adapter
        search_button.setOnClickListener { loadOrder(key_edit.text.toString()) }
        reset_button.setOnClickListener {
            key_edit.setText("")
            search_button.performClick()
        }
        loadOrder()
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_TAG_DISCOVERED == intent?.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG).id
            val cardCode = encodeCardCode(tag)
            loadOrder(cardCode)
            scanFinishAction = { order ->
                when (order) {
                    null -> showToast(getString(R.string.order_of_card_is_empty))
                    else -> submitAction.invoke(order)
                }
            }
        }
    }

    private fun loadOrder(key: String? = null) {
        showProgressDialog()
        OrderRepository.loadOrderList(UserRepository.uid, key ?: "")
                .map { OrderMapper.getOrderList(it.data) }
                .assignSchedulers()
                .doOnNext { dismissProgressDialog() }
                .doOnError { dismissProgressDialog() }
                .subscribeBy(
                        onNext = { loadOrderListSuccess(it) },
                        onError = { onError(throwable = it) })
    }

    private fun submitOrder(order: Order) {
        showProgressDialog()
        OrderRepository.submitOrder(UserRepository.uid, order.id)
                .assignSchedulers()
                .doOnNext { dismissProgressDialog() }
                .doOnError { dismissProgressDialog() }
                .subscribeBy(
                        onNext = {
                            when (it.statusCode) {
                                CommonConst.RESPONSE_CODE_NORMAL -> submitSuccess()
                                else -> onError(message = it.message)
                            }
                        }, onError = { onError(throwable = it) })
    }

    private fun loadOrderListSuccess(orderList: List<Order>) {
        if (scanFinishAction != null) {
            scanFinishAction!!.invoke(orderList.firstOrNull())
            scanFinishAction = null
            return
        }
        val normalList = orderList.filter { it.status == OrderStatus.NORMAL }
        when {
            normalList.isEmpty() -> {
                order_list.visibility = View.GONE
                blank_view.visibility = View.VISIBLE
            }
            else -> {
                order_list.visibility = View.VISIBLE
                blank_view.visibility = View.GONE
                adapter.orderList = normalList
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun submitSuccess() {
        showToast(getString(R.string.success_submit))
        order_list.visibility = View.GONE
        blank_view.visibility = View.VISIBLE
        loadOrder()
    }

    private fun encodeCardCode(tagId: ByteArray): String? {
        val encodeCode = encode(tagId)
        val flipStr = flipHexStr(encodeCode)
        val tempCardNoNumber = java.lang.Long.parseLong(flipStr, 16)
        val size = tempCardNoNumber.toString().length
        val tempCardNo = StringBuilder(tempCardNoNumber.toString())
        for (i in 0 until 10 - size) {
            tempCardNo.insert(0, "0")
        }
        return tempCardNo.toString()
    }

    private fun encode(bytes: ByteArray): String {
        var i: Int
        var j = 0
        var `in`: Int
        val hex = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F")
        val out = StringBuilder()
        while (j < bytes.size) {
            `in` = bytes[j].toInt() and 0xff
            i = `in` shr 4 and 0x0f
            out.append(hex[i])
            i = `in` and 0x0f
            out.append(hex[i])
            ++j
        }
        return out.toString()
    }

    private fun flipHexStr(hexStr: String): String {
        val result = StringBuilder()
        var i = 0
        while (i <= hexStr.length - 2) {
            result.append(StringBuilder(hexStr.substring(i, i + 2)).reverse())
            i += 2
        }
        return result.reverse().toString()
    }
}
