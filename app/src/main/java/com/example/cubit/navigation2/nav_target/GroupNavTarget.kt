/*
package com.example.cubit.navigation2.nav_target

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.cubit.navigation.navigator.GroupNavigator
import com.example.feature_auth.presentation.sign_in.SignInViewModel
import com.example.feature_auth.presentation.sign_in.SingInScreen
import com.example.feature_auth.presentation.sign_up.SignUpViewModel
import com.example.feature_auth.presentation.sign_up.SingUpScreen

object GroupList : NavTarget.Screen(route = "groupList")

class Group(groupId: String) : NavTarget.Screen(route = "group?groupId=$groupId")

fun NavGraphBuilder.groupNavGraph(viewModelNavigator: ViewModelNavigator) {
    composable(route = GroupNavigator.GroupNavTarget.Screen.GroupList.route) {
        val vm = viewModelNavigator.getViewModel() as SignInViewModel
        SingInScreen(viewModel = vm)
    }

    composable(route = GroupNavigator.GroupNavTarget.Screen.Group(groupId = "{groupId}").route) {
        val vm = viewModelNavigator.getViewModel()as SignUpViewModel
        SingUpScreen(viewModel = vm)
    }
}
*/
