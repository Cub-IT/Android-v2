package com.example.cubit.navigation.flow

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.example.core.util.viewModelCreator
import com.example.cubit.navigation.navigator.NavigationFlow
import com.example.cubit.navigation.navigator.Navigator
import com.example.feature_auth.presentation.sign_in.SignInViewModel
import com.example.feature_auth.presentation.sign_up.SignUpViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent

class  SignInNavigationFlow(
    private val activity: ComponentActivity, // TODO: pass lambda instead of the actual objects ???
    private val navigator: Navigator
) : NavigationFlow {

    // injecting ViewModels
    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface SignViewModelFactoryProviderEntryPoint {
        fun signInViewModelFactory(): SignInViewModel.Factory
        fun signUpViewModelFactory(): SignUpViewModel.Factory
    }

    private lateinit var exit: () -> Unit
    private lateinit var signViewModelFactoryProviderEntryPoint: SignViewModelFactoryProviderEntryPoint

    fun start(exit: () -> Unit) {
        this.exit = exit
        signViewModelFactoryProviderEntryPoint = EntryPointAccessors
            .fromActivity(activity, SignViewModelFactoryProviderEntryPoint::class.java)

        navigator.navigateTo(
            navTarget = Navigator.NavTarget.Screen.Auth.SignIn,
            navigationFlow = this
        )
    }

    private fun onSignInScreen(): SignInViewModel {
        return signViewModelFactoryProviderEntryPoint.signInViewModelFactory().create(
            onSignInClicked = exit,
            onSignUpClicked = {
                navigator.navigateTo(
                    navTarget = Navigator.NavTarget.Screen.Auth.SignUp,
                    navigationFlow = this
                )
            }
        )
    }

    private fun onSignUpScreen(): SignUpViewModel {
        return signViewModelFactoryProviderEntryPoint.signUpViewModelFactory().create(
            onSignInClicked = {
                navigator.navigateTo(navTarget = Navigator.NavTarget.Back, navigationFlow = this)
            },
            onSignUpClicked = exit
        )
    }

    override fun <T : ViewModel> getViewModel(modelClass: Class<T>, args: Any?): T? {
        return when (modelClass.name) {
            SignInViewModel::class.java.name -> activity.viewModelCreator { onSignInScreen() }.value
            SignUpViewModel::class.java.name -> activity.viewModelCreator { onSignUpScreen() }.value

            //else -> throw IllegalArgumentException("No ViewModel registered for $modelClass")
            else -> null
        } as? T
    }

}