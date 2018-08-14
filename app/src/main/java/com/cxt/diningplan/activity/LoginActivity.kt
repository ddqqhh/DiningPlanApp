package com.cxt.diningplan.activity

import android.os.Bundle
import com.cxt.diningplan.R
import com.cxt.diningplan.common.CommonConst
import com.cxt.diningplan.enums.NetType
import com.cxt.diningplan.extend.assignSchedulers
import com.cxt.diningplan.repository.UserRepository
import com.cxt.diningplan.extend.startActivity
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseEditTextActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_button.setOnClickListener { _ ->
            showProgressDialog()
            val account = account_edit.text.toString()
            val password = password_edit.text.toString()
            val errorMessage = when {
                account.isEmpty() -> getString(R.string.error_account_is_empty)
                password.isEmpty() -> getString(R.string.error_password_is_empty)
                !NetType.isConnected -> getString(R.string.error_net_is_unavailable)
                else -> null
            }
            when (errorMessage) {
                null -> UserRepository.login(account, password)
                        .assignSchedulers()
                        .doOnNext { dismissProgressDialog() }
                        .doOnError { dismissProgressDialog() }
                        .subscribeBy(
                                onNext = { response ->
                                    when (response.statusCode) {
                                        CommonConst.RESPONSE_CODE_NORMAL -> {
                                            response.result?.let {
                                                UserRepository.uid = it.uid
                                                startActivity(MainActivity::class.java)
                                                finish()
                                            }
                                        }
                                        else -> onError(response.message)
                                    }
                                },
                                onError = { onError(throwable = it) })
            }
        }
    }
}