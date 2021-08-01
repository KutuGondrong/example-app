package com.kutugondrong.exampleapp

import android.app.Application
import com.kutugondrong.exampleapp.di.AppContainer

/**
 * KG KutuGondrong
 * Instance of AppContainer that will be used by all the Activities of the app
 * @see AppContainer
 */
class MyApplication : Application() {
    val appContainer = AppContainer()
}