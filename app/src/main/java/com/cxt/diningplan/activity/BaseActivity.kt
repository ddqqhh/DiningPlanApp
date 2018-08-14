package com.cxt.diningplan.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import com.cxt.diningplan.R
import com.cxt.diningplan.extend.startActivity

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    private val progressDialog by lazy {
        ProgressDialog(this)
                .apply {
                    setCancelable(false)
                    setCanceledOnTouchOutside(false)
                    setMessage(getString(R.string.progress_dialog_waiting))
                }
    }

    fun moveToLogin() {
        startActivity(LoginActivity::class.java)
        finish()
    }

    fun onError(message: String? = null, throwable: Throwable? = null) {
        throwable?.printStackTrace()
        AlertDialog.Builder(this)
                .setMessage(message ?: getString(R.string.error_request_failure))
                .setPositiveButton(R.string.ok, null)
                .show()
    }

    fun showProgressDialog() = progressDialog.show()

    fun dismissProgressDialog() = progressDialog.dismiss()
}