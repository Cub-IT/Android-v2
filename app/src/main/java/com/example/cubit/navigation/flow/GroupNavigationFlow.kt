package com.example.cubit.navigation.flow

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.example.core.util.viewModelCreator
import com.example.cubit.navigation.navigator.NavigationFlow
import com.example.cubit.navigation.navigator.Navigator
import com.example.feature_group.presentation.add_group.AddGroupViewModel
import com.example.feature_group.presentation.group.GroupViewModel
import com.example.feature_group.presentation.group_list.GroupListViewModel
import com.example.feature_group.presentation.user.UserViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent

class GroupNavigationFlow constructor(
    private val activity: ComponentActivity,
    private val navigator: Navigator
) : NavigationFlow {

    // injecting ViewModels
    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface GroupNavigationFlowProviderEntryPoint {
        fun groupListViewModelFactory(): GroupListViewModel.Factory
        fun groupViewModelFactory(): GroupViewModel.Factory
        fun userViewModelFactory(): UserViewModel.Factory
        fun addGroupViewModelFactory(): AddGroupViewModel.Factory
    }

    private lateinit var exit: () -> Unit
    private lateinit var groupNavigationFlowProviderEntryPoint: GroupNavigationFlowProviderEntryPoint

    fun start(exit: () -> Unit) {
        this.exit = exit
        groupNavigationFlowProviderEntryPoint = EntryPointAccessors
            .fromActivity(activity, GroupNavigationFlowProviderEntryPoint::class.java)

        navigator.navigateTo(
            navTarget = Navigator.NavTarget.Screen.Group.GroupList,
            navigationFlow = this
        )
    }

    private fun onGroupListScreen(): GroupListViewModel {
        return groupNavigationFlowProviderEntryPoint.groupListViewModelFactory().create(
            onGroupClicked = { groupId ->
                navigator.navigateTo(
                    navTarget = Navigator.NavTarget.Screen.Group.Group(groupId = groupId),
                    navigationFlow = this
                )
            },
            onUserAvatarClicked = {
                navigator.navigateTo(
                    navTarget = Navigator.NavTarget.Screen.Group.User,
                    navigationFlow = this
                )
            },
            onFabClicked = {
                navigator.navigateTo(
                    navTarget = Navigator.NavTarget.Screen.Group.AddGroup,
                    navigationFlow = this
                )
            }
        )
    }

    private fun onGroupScreen(): GroupViewModel {
        return groupNavigationFlowProviderEntryPoint.groupViewModelFactory().create(
            onBackClicked = {
                navigator.navigateTo(
                    navTarget = Navigator.NavTarget.Screen.Group.GroupList,
                    navigationFlow = this
                )
            },
            onUserAvatarClicked = {
                navigator.navigateTo(
                    navTarget = Navigator.NavTarget.Screen.Group.User,
                    navigationFlow = this
                )
            }
        )
    }

    private fun onUserScreen(): UserViewModel {
        return groupNavigationFlowProviderEntryPoint.userViewModelFactory().create(
            onBackClicked = {
                navigator.navigateTo(
                    navTarget = Navigator.NavTarget.Back,
                    navigationFlow = this
                )
            },
            onLogoutClicked = { exit() }
        )
    }

    private fun onAddGroupScreen(): AddGroupViewModel {
        return groupNavigationFlowProviderEntryPoint.addGroupViewModelFactory().create(
            onCreateClicked = {
                navigator.navigateTo(
                    navTarget = Navigator.NavTarget.Back,
                    navigationFlow = this
                )
            },
            onBackClicked = {
                navigator.navigateTo(
                    navTarget = Navigator.NavTarget.Back,
                    navigationFlow = this
                )
            }
        )
    }

    override fun <T : ViewModel> getViewModel(modelClass: Class<T>): T? {
        return when (modelClass) {
            GroupListViewModel::class.java -> activity.viewModelCreator { onGroupListScreen() }.value
            GroupViewModel::class.java -> activity.viewModelCreator { onGroupScreen() }.value
            UserViewModel::class.java -> activity.viewModelCreator { onUserScreen() }.value
            AddGroupViewModel::class.java -> activity.viewModelCreator { onAddGroupScreen() }.value

            //else -> throw IllegalArgumentException("No ViewModel registered for $modelClass")
            else -> null
        } as? T
    }

}