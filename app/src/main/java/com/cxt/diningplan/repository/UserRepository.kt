package com.cxt.diningplan.repository

import com.cxt.diningplan.PreferenceManage
import com.cxt.diningplan.api.ApiClientCreator
import com.cxt.diningplan.api.UserApiClient

object UserRepository {

    private const val USER_ID = "uid"

    private val userApiClient = ApiClientCreator.createBuilder(10).create(UserApiClient::class.java)

    var uid: Long
        get() = PreferenceManage.getLong(USER_ID)
        set(value) {
            PreferenceManage.putLong(USER_ID, value)
        }

    fun clear() {
        PreferenceManage.remove(USER_ID)
    }

    fun login(account: String, password: String) = userApiClient.login(account, password)
}