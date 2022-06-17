package com.example.core.data.local

import com.example.core.presentation.item.UserItem

interface UserSource {

    fun isAuthorized(): Boolean = getUser() != null

    fun getUser(): UserItem?

    fun saveUser(userItem: UserItem)

    fun deleteUser()

}