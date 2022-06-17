package com.example.core.data.local

import com.example.core.presentation.item.UserItem

interface UserSource {

    suspend fun isSignedIn(): Boolean = getUser() != null

    suspend fun getUser(): UserItem?

    suspend fun saveUser(userItem: UserItem)

    suspend fun deleteUser()

}