package com.kutugondrong.exampleapp.screen.main

import com.kutugondrong.exampleapp.factory.Factory
import com.kutugondrong.exampleapp.di.AppContainer

/**
 * KG KutuGondrong
 * MainViewModelFactory is class for make register MainViewModel to AppContainer
 * @see MainViewModel
 *
 *
 * @see AppContainer
 */
class MainViewModelFactory(
    private val repository: MainRepository
) : Factory<MainViewModel> {

    override fun create(): MainViewModel {
        return MainViewModel(repository)
    }

}