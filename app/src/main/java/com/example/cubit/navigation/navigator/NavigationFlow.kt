package com.example.cubit.navigation.navigator

import androidx.lifecycle.ViewModel

interface NavigationFlow {
    // TODO: change back to getViewModel(modelClass: Class<T>): T       <-- without "?"
    fun <T : ViewModel> getViewModel(modelClass: Class<T>): T?
}