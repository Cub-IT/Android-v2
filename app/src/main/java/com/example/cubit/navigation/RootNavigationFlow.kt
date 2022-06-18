package com.example.cubit.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.core.data.local.UserSource
import com.example.cubit.navigation.flow.GroupNavigationFlow
import com.example.cubit.navigation.flow.SignInNavigationFlow
import com.example.cubit.navigation.navigator.AuthNavigator
import javax.inject.Inject

class RootNavigationFlow(
    private val activity: ComponentActivity
) {

    @Inject
    lateinit var userSource: UserSource

    @Composable
    fun start() {
        val navController = rememberNavController()
        val signInNavigationFlow = SignInNavigationFlow(activity, navController)
        val groupNavigationFlow = GroupNavigationFlow(activity, navController)

        NavHost(
            navController = navController,
            startDestination = "",
            route = ""
        ) {
            authNavigator.navGraph
            groupNavigator.navGraph
        }

        if (userSource.isAuthorized()) {
            signInNavigationFlow.start { this.start() }
        } else {
            groupNavigationFlow.start { this.start() }
        }
    }

    @Composable
    private fun setupNavGraph(navController: NavHostController) {
        val authNavigator = AuthNavigator()

        NavHost(
            navController = navController,
            startDestination = "",
            route = ""
        ) {
            authNavigator.navGraph
            groupNavigator.navGraph
        }
    }

}