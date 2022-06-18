package com.example.cubit.navigation.flow

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.example.core.util.viewModelCreator
import com.example.cubit.navigation.navigator.NavigationFlow
import com.example.cubit.navigation.navigator.Navigator
import com.example.feature_group.presentation.group.GroupViewModel
import com.example.feature_group.presentation.group_list.GroupListViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent

class GroupNavigationFlow constructor(
    private val activity: ComponentActivity,
    private val navigator: Navigator
) : NavigationFlow {

    // injecting userSource
    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface GroupNavigationFlowProviderEntryPoint {
        fun groupListViewModelFactory(): GroupListViewModel.Factory
        fun groupViewModelFactory(): GroupViewModel.Factory
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
            onUserAvatarClicked = { /* TODO: implement user profile screen */ }
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
            onUserAvatarClicked = { /* TODO: implement user profile screen */ }
        )
    }

    override fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return when (modelClass) {
            GroupListViewModel::class.java -> activity.viewModelCreator { onGroupListScreen() }.value
            GroupViewModel::class.java -> activity.viewModelCreator { onGroupScreen() }.value

            else -> throw IllegalArgumentException("No ViewModel registered for $modelClass")
        } as T
    }

}