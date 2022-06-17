package com.example.feature_auth.data.local

import android.content.Context
import com.example.core.data.local.UserSource
import com.example.core.presentation.item.UserItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataStore @Inject constructor(
    @ApplicationContext private val appContext: Context
) : UserSource {

    override suspend fun getUser(): UserItem? {
        TODO("Not yet implemented")
    }

    override suspend fun saveUser(userItem: UserItem) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser() {
        TODO("Not yet implemented")
    }

}