package com.cxt.diningplan.extend

import android.content.res.Resources
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun Float.toPx(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun Int.toPx() = this.toFloat().toPx()

fun String.with(vararg parts: String) = String.format(this, *parts)

fun <T> Observable<T>.assignSchedulers(): Observable<T> = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())