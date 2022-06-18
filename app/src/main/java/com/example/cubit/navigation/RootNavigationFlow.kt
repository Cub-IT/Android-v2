package com.example.cubit.navigation

import androidx.activity.ComponentActivity
import com.example.core.data.local.UserSource
import com.example.cubit.navigation.flow.GroupNavigationFlow
import com.example.cubit.navigation.flow.SignInNavigationFlow
import com.example.cubit.navigation.navigator.Navigator
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent

class RootNavigationFlow(
    private val activity: ComponentActivity,
    private val navigator: Navigator
) {

    // injecting userSource
    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface UserSourceProviderEntryPoint {
        fun userSource(): UserSource
    }

    fun start() {
        val signInNavigationFlow = SignInNavigationFlow(activity, navigator)
        val groupNavigationFlow = GroupNavigationFlow(activity, navigator)
        val userSource = EntryPointAccessors
            .fromActivity(activity, UserSourceProviderEntryPoint::class.java)
            .userSource()

        if (userSource.isAuthorized()) {
            groupNavigationFlow.start { this.start() }
        } else {
            signInNavigationFlow.start { this.start() }
        }
    }

}
