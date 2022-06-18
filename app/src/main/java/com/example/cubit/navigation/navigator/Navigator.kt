package com.example.cubit.navigation.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.feature_auth.presentation.sign_in.SignInViewModel
import com.example.feature_auth.presentation.sign_in.SingInScreen
import com.example.feature_auth.presentation.sign_up.SignUpViewModel
import com.example.feature_auth.presentation.sign_up.SingUpScreen

class Navigator (
    private val navController: NavHostController
) {

    private var navigationFlow: NavigationFlow? = null

    fun navigateTo(
        navTarget: NavTarget,
        navigationFlow: NavigationFlow
    ) {
        this.navigationFlow = navigationFlow

        when (navTarget) {
            is NavTarget.Back -> navController.navigateUp()
            is NavTarget.Screen -> navController.navigate(navTarget.route)
        }
    }

    sealed class NavTarget {
        object Back : NavTarget()

        sealed class Screen(val route: String) : NavTarget() {
            sealed class Auth(route: String) : Screen(route = route) {
                object SignIn : Auth(route = "signIn")

                object SignUp : Auth(route = "signUp")
            }

            sealed class Group(route: String) : Screen(route = route) {
                object GroupList : Screen.Group(route = "groupList")

                data class Group(val groupId: String) : Screen.Group(route = "group?groupId=$groupId")
            }
        }
    }

    @Composable
    fun SetupNavGraph() { // TODO: maybe move composable destinations to separate classes ???
        NavHost(
            navController = navController,
            startDestination = NavTarget.Screen.Auth.SignIn.route,
        ) {
            composable(route = NavTarget.Screen.Auth.SignIn.route) {
                val vm = navigationFlow?.getViewModel(modelClass = SignInViewModel::class.java)
                    ?: throw IllegalStateException()
                //navigationFlow = null
                SingInScreen(viewModel = vm)
            }

            composable(route = NavTarget.Screen.Auth.SignUp.route) {
                val vm = navigationFlow?.getViewModel(modelClass = SignUpViewModel::class.java)
                    ?: throw IllegalStateException()
                //navigationFlow = null
                SingUpScreen(viewModel = vm)
            }

            composable(route = NavTarget.Screen.Group.GroupList.route) {
                val vm = navigationFlow?.getViewModel(modelClass = SignInViewModel::class.java)
                    ?: throw IllegalStateException()
                //navigationFlow = null
                SingInScreen(viewModel = vm)
            }

            composable(route = NavTarget.Screen.Group.Group(groupId = "{groupId}").route) {
                val vm = navigationFlow?.getViewModel(modelClass = SignUpViewModel::class.java)
                    ?: throw IllegalStateException()
                //navigationFlow = null
                SingUpScreen(viewModel = vm)
            }
        }
    }

}