package com.example.feature_auth.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.core.data.local.UserSource
import com.example.core.presentation.item.UserItem
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSharedPreferences @Inject constructor(
    @ApplicationContext private val appContext: Context
) : UserSource {

    private val USER_SHARED_PREFERENCES = "USER_SHARED_PREFERENCES"
    private val USER_KEY = "USER_KEY"

    private val sharedPreferences = appContext.getSharedPreferences(USER_SHARED_PREFERENCES, MODE_PRIVATE)
    private val gson = Gson()

    override fun getUser(): UserItem? {
        return gson.fromJson(
            sharedPreferences.getString(USER_KEY, null),
            UserItem::class.java
        )
    }

    @SuppressLint("CommitPrefEdits")
    override fun saveUser(userItem: UserItem) {
        sharedPreferences.edit()
            .putString(
                USER_KEY,
                gson.toJson(userItem)
            )
            .apply()
    }

    override fun deleteUser() {
        sharedPreferences.edit()
            .remove(USER_KEY)
            .apply()
    }

}