package com.example.cubit.navigation.navigator

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.feature_auth.presentation.sign_in.SignInViewModel
import com.example.feature_auth.presentation.sign_in.SingInScreen
import com.example.feature_auth.presentation.sign_up.SignUpViewModel
import com.example.feature_auth.presentation.sign_up.SingUpScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthNavigator @Inject constructor(
    private val navController: NavController,
    private val navigationFlow: NavigationFlow
) {

    fun navigateTo(authNavTarget: AuthNavTarget) {
        when (authNavTarget) {
            is AuthNavTarget.Back -> navController.navigateUp()
            is AuthNavTarget.Screen -> navController.navigate(authNavTarget.route)
        }
    }

    sealed class AuthNavTarget {
        object Back : AuthNavTarget()

        sealed class Screen(val route: String) : AuthNavTarget() {
            object SignIn : Screen(route = "signIn")

            object SignUp : Screen(route = "signUp")
        }
    }

    val navGraph: NavGraphBuilder.() -> Unit = {
        navigation(
            startDestination = navigationFlow.getStartDestination(),
            route = AUTH_ROUTE
        ) {
            composable(route = AuthNavTarget.Screen.SignIn.route) {
                val vm = navigationFlow.getViewModel(modelClass = SignInViewModel::class.java)
                SingInScreen(viewModel = vm)
            }

            composable(route = AuthNavTarget.Screen.SignUp.route) {
                val vm = navigationFlow.getViewModel(modelClass = SignUpViewModel::class.java)
                SingUpScreen(viewModel = vm)
            }
        }
    }

    companion object {
        const val AUTH_ROUTE = "authRoute"
    }

}
