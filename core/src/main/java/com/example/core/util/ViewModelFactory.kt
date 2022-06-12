package com.example.core.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

typealias ViewModelCreator<VM> = () -> VM

class ViewModelFactory<VM : ViewModel>(
    private val viewModelCreator: ViewModelCreator<VM>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelCreator() as T
    }

}