package com.example.cubit.navigation.navigator

import androidx.lifecycle.ViewModel

interface NavigationFlow {
    fun <T : ViewModel> getViewModel(modelClass: Class<T>): T
}