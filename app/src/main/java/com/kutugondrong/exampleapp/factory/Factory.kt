package com.kutugondrong.exampleapp.factory

import com.kutugondrong.exampleapp.di.AppContainer

/**
 * KG KutuGondrong
 * Definition of a Factory interface with a function to create objects of a type
 *
 *
 *
 * @see AppContainer
 */
interface Factory<T> {
    fun create(): T
}
