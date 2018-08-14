package com.cxt.diningplan.extend

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast

fun Activity.startActivity(target: Class<*>) {
    startActivity(target) {}
}

inline fun Activity.startActivity(target: Class<*>, putData: (Intent) -> Unit) {
    val intent = Intent(this, target)
    putData(intent)
    startActivity(intent)
}

fun Activity.startActivityForResult(target: Class<*>, requestCode: Int) {
    startActivityForResult(target, requestCode) {}
}

inline fun Activity.startActivityForResult(target: Class<*>, requestCode: Int, putData: (Intent) -> Unit) {
    val intent = Intent(this, target)
    putData(intent)
    startActivityForResult(intent, requestCode)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}