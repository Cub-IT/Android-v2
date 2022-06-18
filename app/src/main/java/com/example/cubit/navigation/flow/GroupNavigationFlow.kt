package com.example.cubit.navigation.flow

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.core.util.viewModelCreator
import com.example.cubit.navigation.navigator.GroupNavigator
import com.example.cubit.navigation.navigator.NavigationFlow
import com.example.feature_group.presentation.group.GroupViewModel
import com.example.feature_group.presentation.group_list.GroupListViewModel
import javax.inject.Inject

class GroupNavigationFlow constructor(
    private val activity: ComponentActivity,
    private val navController: NavController
) : NavigationFlow {

    @Inject
    lateinit var groupListViewModelFactory: GroupListViewModel.Factory
    @Inject
    lateinit var groupViewModelFactory: GroupViewModel.Factory

    private lateinit var exit: () -> Unit
    private lateinit var groupNavigator: GroupNavigator

    fun start(exit: () -> Unit) {
        this.exit = exit
        groupNavigator = GroupNavigator(navController = navController, navigationFlow = this)
        groupNavigator.navigateTo(
            GroupNavigator.GroupNavTarget.Screen.GroupList
        )
    }

    private fun onGroupListScreen(): GroupListViewModel {
        return groupListViewModelFactory.create(
            onGroupClicked = { groupId ->
                groupNavigator.navigateTo(GroupNavigator.GroupNavTarget.Screen.Group(groupId = groupId))
            },
            onUserAvatarClicked = { /* TODO: implement user profile screen */ }
        )
    }

    private fun onGroupScreen(): GroupViewModel {
        return groupViewModelFactory.create(
            onBackClicked = { groupNavigator.navigateTo(GroupNavigator.GroupNavTarget.Screen.GroupList) },
            onUserAvatarClicked = { /* TODO: implement user profile screen */ }
        )
    }

    override fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return when (modelClass) {
            GroupListViewModel::class.java -> activity.viewModelCreator { onGroupListScreen() }
            GroupViewModel::class.java -> activity.viewModelCreator { onGroupScreen() }

            else -> throw IllegalArgumentException("No ViewModel registered for $modelClass")
        } as T
    }

    override fun getStartDestination(): String {
        return "" // TODO
    }


}