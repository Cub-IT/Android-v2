package com.example.cubit.navigation.navigator

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.feature_auth.presentation.sign_in.SignInViewModel
import com.example.feature_auth.presentation.sign_in.SingInScreen
import com.example.feature_auth.presentation.sign_up.SignUpViewModel
import com.example.feature_auth.presentation.sign_up.SingUpScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupNavigator @Inject constructor(
    private val navController: NavController,
    private val navigationFlow: NavigationFlow
) {

    fun navigateTo(groupNavTarget: GroupNavTarget) {
        when (groupNavTarget) {
            is GroupNavTarget.Back -> navController.navigateUp()
            is GroupNavTarget.Screen -> navController.navigate(groupNavTarget.route)
        }
    }

    sealed class GroupNavTarget {
        object Back : GroupNavTarget()

        sealed class Screen(val route: String) : GroupNavTarget() {
            object GroupList : Screen(route = "groupList")

            data class Group(val groupId: String) : Screen(route = "group?groupId=$groupId")
        }
    }

    val navGraph: NavGraphBuilder.() -> Unit = {
        navigation(
            startDestination = navigationFlow.getStartDestination(),
            route = GROUP_ROUTE
        ) {
            composable(route = GroupNavTarget.Screen.GroupList.route) {
                val vm = navigationFlow.getViewModel(modelClass = SignInViewModel::class.java)
                SingInScreen(viewModel = vm)
            }

            composable(route = GroupNavTarget.Screen.Group(groupId = "{groupId}").route) {
                val vm = navigationFlow.getViewModel(modelClass = SignUpViewModel::class.java)
                SingUpScreen(viewModel = vm)
            }
        }
    }

    companion object {
        const val GROUP_ROUTE = "groupRoute"
    }

}
