/*
package com.example.cubit.navigation2.nav_target

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature_auth.presentation.sign_in.SignInViewModel
import com.example.feature_auth.presentation.sign_in.SingInScreen
import com.example.feature_auth.presentation.sign_up.SignUpViewModel
import com.example.feature_auth.presentation.sign_up.SingUpScreen

object SignIn : NavTarget.Screen(route = "signIn")

object SignUp : NavTarget.Screen(route = "signUp")

fun NavGraphBuilder.authNavGraph(viewModelNavigator: ViewModelNavigator) {
    composable(route = AuthNavigator.AuthNavTarget.Screen.SignIn.route) {
        val vm = viewModelNavigator.getViewModel() as SignInViewModel
        SingInScreen(viewModel = vm)
    }

    composable(route = AuthNavigator.AuthNavTarget.Screen.SignUp.route) {
        val vm = viewModelNavigator.getViewModel() as SignUpViewModel
        SingUpScreen(viewModel = vm)
    }
}*/
