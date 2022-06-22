package com.example.cubit.navigation.navigator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.feature_auth.presentation.sign_in.SignInViewModel
import com.example.feature_auth.presentation.sign_in.SingInScreen
import com.example.feature_auth.presentation.sign_up.SignUpViewModel
import com.example.feature_auth.presentation.sign_up.SingUpScreen
import com.example.feature_group.presentation.add_group.AddGroupScreen
import com.example.feature_group.presentation.add_group.AddGroupViewModel
import com.example.feature_group.presentation.add_post.AddPostScreen
import com.example.feature_group.presentation.add_post.AddPostViewModel
import com.example.feature_group.presentation.edit_post.EditPostScreen
import com.example.feature_group.presentation.edit_post.EditPostViewModel
import com.example.feature_group.presentation.group.GroupScreen
import com.example.feature_group.presentation.group.GroupViewModel
import com.example.feature_group.presentation.group_list.GroupListScreen
import com.example.feature_group.presentation.group_list.GroupListViewModel
import com.example.feature_group.presentation.join_group.JoinGroupScreen
import com.example.feature_group.presentation.join_group.JoinGroupViewModel
import com.example.feature_group.presentation.user.UserScreen
import com.example.feature_group.presentation.user.UserViewModel

class Navigator (
    private val navController: NavHostController
) {

    private var previousNavigationFLow: NavigationFlow? = null
    private var navigationFlow: NavigationFlow? = null

    fun navigateTo(
        navTarget: NavTarget,
        navigationFlow: NavigationFlow
    ) {
        if (this.navigationFlow != navigationFlow) {
            previousNavigationFLow = this.navigationFlow
        }
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

                data class Group(val groupId: String) : Screen.Group(route = "group/$groupId")

                object User : Screen(route = "user")

                object AddGroup : Screen.Group(route = "addGroup")

                object JoinGroup : Screen.Group(route = "joinGroup")

                data class AddPost(val groupId: String) : Screen.Group(route = "addPost/$groupId")

                data class EditPost(val postId: String) : Screen.Group(route = "editPost/$postId")
            }
        }
    }

    @Composable
    fun SetupNavGraph() { // TODO: maybe move composable destinations to separate classes ???
        NavHost(
            navController = navController,
            startDestination = NavTarget.Screen.Auth.SignIn.route // TODO: it just to fill the gap. this dest is never called
        ) {
            composable(route = NavTarget.Screen.Auth.SignIn.route) {
                val vm = navigationFlow?.getViewModel(modelClass = SignInViewModel::class.java)
                    ?: previousNavigationFLow?.getViewModel(modelClass = SignInViewModel::class.java) // TODO: get rid of it
                    ?: throw IllegalStateException()
                //navigationFlow = null
                SingInScreen(viewModel = vm)
            }

            composable(route = NavTarget.Screen.Auth.SignUp.route) {
                val vm = navigationFlow?.getViewModel(modelClass = SignUpViewModel::class.java)
                    ?: previousNavigationFLow?.getViewModel(modelClass = SignUpViewModel::class.java) // TODO: get rid of it
                    ?: throw IllegalStateException()
                //navigationFlow = null
                SingUpScreen(viewModel = vm)
            }

            composable(route = NavTarget.Screen.Group.GroupList.route) {
                val vm = navigationFlow?.getViewModel(modelClass = GroupListViewModel::class.java)
                    ?: previousNavigationFLow?.getViewModel(modelClass = GroupListViewModel::class.java) // TODO: get rid of it
                    ?: throw IllegalStateException()
                //navigationFlow = null
                GroupListScreen(viewModel = vm)
            }

            composable(
                route = NavTarget.Screen.Group.Group(groupId = "{groupId}").route,
                arguments = listOf(navArgument("groupId") { type = NavType.StringType })
            ) { backStackEntry ->
                val groupId = backStackEntry.arguments?.getString("groupId") ?: throw IllegalArgumentException()
                val vm = navigationFlow?.getViewModel(modelClass = GroupViewModel::class.java, groupId)
                    ?: previousNavigationFLow?.getViewModel(modelClass = GroupViewModel::class.java, groupId) // TODO: get rid of it
                    ?: throw IllegalStateException()
                //navigationFlow = null
                vm.groupId = groupId
                GroupScreen(viewModel = vm)
            }

            composable(route = NavTarget.Screen.Group.User.route) {
                val vm = navigationFlow?.getViewModel(modelClass = UserViewModel::class.java)
                    ?: previousNavigationFLow?.getViewModel(modelClass = UserViewModel::class.java) // TODO: get rid of it
                    ?: throw IllegalStateException()
                //navigationFlow = null
                UserScreen(viewModel = vm)
            }

            composable(route = NavTarget.Screen.Group.AddGroup.route) {
                val vm = navigationFlow?.getViewModel(modelClass = AddGroupViewModel::class.java)
                    ?: previousNavigationFLow?.getViewModel(modelClass = AddGroupViewModel::class.java) // TODO: get rid of it
                    ?: throw IllegalStateException()
                //navigationFlow = null
                AddGroupScreen(viewModel = vm)
            }

            composable(route = NavTarget.Screen.Group.JoinGroup.route) {
                val vm = navigationFlow?.getViewModel(modelClass = JoinGroupViewModel::class.java)
                    ?: previousNavigationFLow?.getViewModel(modelClass = JoinGroupViewModel::class.java) // TODO: get rid of it
                    ?: throw IllegalStateException()
                //navigationFlow = null
                JoinGroupScreen(viewModel = vm)
            }

            composable(
                route = NavTarget.Screen.Group.AddPost(groupId = "{groupId}").route,
                arguments = listOf(navArgument("groupId") { type = NavType.StringType })
            ) { backStackEntry ->
                val groupId = backStackEntry.arguments?.getString("groupId") ?: throw IllegalArgumentException()
                val vm = navigationFlow?.getViewModel(modelClass = AddPostViewModel::class.java, groupId)
                    ?: previousNavigationFLow?.getViewModel(modelClass = AddPostViewModel::class.java, groupId) // TODO: get rid of it
                    ?: throw IllegalStateException()
                vm.groupId = groupId
                //navigationFlow = null
                AddPostScreen(viewModel = vm)
            }

            composable(
                route = NavTarget.Screen.Group.EditPost(postId = "{postId}").route,
                arguments = listOf(navArgument("postId") { type = NavType.StringType })
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId") ?: throw IllegalArgumentException()
                val vm = navigationFlow?.getViewModel(modelClass = EditPostViewModel::class.java, postId)
                    ?: previousNavigationFLow?.getViewModel(modelClass = EditPostViewModel::class.java, postId) // TODO: get rid of it
                    ?: throw IllegalStateException()
                vm.postId = postId
                //navigationFlow = null
                EditPostScreen(viewModel = vm)
            }
        }
    }

}