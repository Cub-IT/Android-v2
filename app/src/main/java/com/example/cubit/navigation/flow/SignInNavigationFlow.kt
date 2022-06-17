package com.example.cubit.navigation.flow

import androidx.lifecycle.ViewModel
import com.example.cubit.MainActivity
import com.example.cubit.navigation.navigator.AuthNavigator
import com.example.feature_auth.presentation.sign_in.SignInViewModel
import com.example.feature_auth.presentation.sign_up.SignUpViewModel
import javax.inject.Inject

class  SignInNavigationFlow @Inject constructor(
    //private val exit: () -> Unit,
    private val SignInViewModelFactory: SignInViewModel.Factory,
    private val SignUpViewModelFactory: SignUpViewModel.Factory,
    private val authNavigator: AuthNavigator,
    private val activity: MainActivity
) : NavigationFlow {

    private lateinit var exit: () -> Unit

    fun start(exit: () -> Unit) {
        this.exit = exit
        authNavigator.navigateTo(
            AuthNavigator.AuthNavTarget.Screen.SignIn
        )
    }

    override fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return when (modelClass) {
            SignInViewModel::class.java -> {
                activity.viewModelsCreator { // TODO:  can't get viewModelsCreator here
                    SignInViewModelFactory.create(
                        onSignInClicked = exit,
                        onSignUpClicked = {
                            authNavigator.navigateTo(AuthNavigator.AuthNavTarget.Screen.SignUp)
                        }
                    )
                }
            }
            SignUpViewModel::class.java -> {
                // here goes an implementation idea (only the idea how viewModel looks like!)
                activity.viewModelsCreator { // TODO:  can't get viewModelsCreator here
                    SignUpViewModelFactory.create(
                        onSignInClicked = {
                            authNavigator.navigateTo(AuthNavigator.AuthNavTarget.Back)
                        },
                        onSignUpClicked = {
                            authNavigator.navigateTo(AuthNavigator.AuthNavTarget.Back)
                        }
                    )
                }
            }
            else -> {
                throw IllegalArgumentException("No ViewModel registered for $modelClass")
            }
        } as T
    }

}