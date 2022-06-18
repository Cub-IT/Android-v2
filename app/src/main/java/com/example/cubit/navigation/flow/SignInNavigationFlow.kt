package com.example.cubit.navigation.flow

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.core.util.viewModelCreator
import com.example.cubit.navigation.navigator.AuthNavigator
import com.example.cubit.navigation.navigator.GroupNavigator
import com.example.cubit.navigation.navigator.NavigationFlow
import com.example.feature_auth.presentation.sign_in.SignInViewModel
import com.example.feature_auth.presentation.sign_up.SignUpViewModel
import javax.inject.Inject

class  SignInNavigationFlow constructor(
    private val activity: ComponentActivity, // TODO: pass lambda instead of the actual objects
    private val navController: NavController
) : NavigationFlow {

    @Inject
    lateinit var signInViewModelFactory: SignInViewModel.Factory
    @Inject
    lateinit var signUpViewModelFactory: SignUpViewModel.Factory

    private lateinit var exit: () -> Unit
    private lateinit var authNavigator: AuthNavigator

    fun start(exit: () -> Unit) {
        this.exit = exit
        authNavigator = AuthNavigator(navController = navController, navigationFlow = this)
        authNavigator.navigateTo(
            AuthNavigator.AuthNavTarget.Screen.SignIn
        )
    }

    private fun onSignInScreen(): SignInViewModel {
        return signInViewModelFactory.create(
            onSignInClicked = exit,
            onSignUpClicked = { authNavigator.navigateTo(AuthNavigator.AuthNavTarget.Screen.SignUp) }
        )
    }

    private fun onSignUpScreen(): SignUpViewModel {
        return signUpViewModelFactory.create(
            onSignInClicked = { authNavigator.navigateTo(AuthNavigator.AuthNavTarget.Back) },
            onSignUpClicked = { authNavigator.navigateTo(AuthNavigator.AuthNavTarget.Back) }
        )
    }

    override fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return when (modelClass) {
            SignInViewModel::class.java -> activity.viewModelCreator { onSignInScreen() }
            SignUpViewModel::class.java -> activity.viewModelCreator { onSignUpScreen() }

            else -> throw IllegalArgumentException("No ViewModel registered for $modelClass")
        } as T
    }

    override fun getStartDestination(): String {
        return "" //TODO("Not yet implemented")
    }

}