package com.example.core.data.local

import com.example.core.presentation.item.UserItem
import okhttp3.Cookie

interface UserSource {

    fun isAuthorized(): Boolean = getUser() != null

    fun getUser(): UserItem?

    fun saveUser(userItem: UserItem)

    fun deleteUser()

    fun getCookies(): List<Cookie>

    fun saveCookies(cookies: List<Cookie>)

}