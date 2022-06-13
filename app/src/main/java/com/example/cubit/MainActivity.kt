package com.example.cubit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.core.presentation.theme.CubITTheme
import com.example.core.util.ViewModelCreator
import com.example.core.util.ViewModelFactory
import com.example.cubit.navigation.navigator.AuthNavigator
import com.example.cubit.navigation.navigator.GroupNavigator
import com.example.feature_auth.presentation.sign_in.SignInViewModel
import com.example.feature_auth.presentation.sign_in.SingInScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    inline fun <reified VM : ViewModel> ComponentActivity.viewModelCreator(
        noinline creator: ViewModelCreator<VM>
    ): Lazy<VM> {
        return viewModels { ViewModelFactory(creator) }
    }

    @Inject
    lateinit var factory: SignInViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CubITTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")

                    val viewModel by viewModelCreator {
                        factory.create(
                            onSignInClicked = {},
                            onSignUpClicked = {}
                        )
                    }
                    SingInScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
private fun NavigationComponent(
    navController: NavHostController,
    authNavigator: AuthNavigator,
    groupNavigator: GroupNavigator
) {
    /*LaunchedEffect(Unit) {
        authNavigator.sharedFlow.onEach { navTarget ->
            when (navTarget) {
                is AuthNavigator.NavTarget.Back -> navController.navigateUp()
                is AuthNavigator.NavTarget.SignIn -> {
                    val t = navTarget
                    navController.navigate(navTarget.route, navigatorExtras = Navigator.Extras.)
                }
                is AuthNavigator.NavTarget.SignUp -> TODO()
            }.exhaustive
        }
    }*/

    NavHost(
        navController = navController,
        startDestination = "/* TODO */" //AuthNavigator.NavTarget.SignIn().route
    ) {
        authNavigator.navGraph
        groupNavigator.navGraph
    }
}
