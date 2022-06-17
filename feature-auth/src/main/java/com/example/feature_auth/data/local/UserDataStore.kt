package com.example.feature_auth.data.local

import android.content.Context
import com.example.core.data.local.UserSource
import com.example.core.presentation.item.UserItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataStore @Inject constructor(
    @ApplicationContext private val appContext: Context
) : UserSource {

    override fun getUser(): UserItem? {
        return null
        TODO("Not yet implemented")
    }

    override fun saveUser(userItem: UserItem) {
        TODO("Not yet implemented")
    }

    override fun deleteUser() {
        TODO("Not yet implemented")
    }

}