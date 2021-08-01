package com.kutugondrong.exampleapp.di

import com.kutugondrong.exampleapp.BuildConfig
import com.kutugondrong.exampleapp.MyApplication
import com.kutugondrong.exampleapp.network.PhotoService
import com.kutugondrong.exampleapp.screen.main.MainActivity
import com.kutugondrong.exampleapp.screen.main.MainRepository
import com.kutugondrong.exampleapp.screen.main.MainViewModelFactory
import com.kutugondrong.httpclientkg.HttpClientKG
import com.kutugondrong.jsonkgadapter.JsonKGAdapter
import com.kutugondrong.networkkg.NetworkKG

/**
 * KG KutuGondrong
 * Container of objects shared across the whole app
 * MainActivity is class default launcher Application
 * @see MainActivity
 *
 *
 * @see MyApplication
 */
class AppContainer {

    /**
     * Http Request using NetworkKg
     * @see NetworkKG
     */
    private val service = NetworkKG.dslNetworkKG{
        httpClient = HttpClientKG.dslDefaultHttpClient {
            baseUrl = BuildConfig.SERVER_BASE_URL
            properties {
                property {
                    key = "Authorization"
                    value = "Client-ID ${BuildConfig.API_KEY}"
                }
            }
        }
        //See below the json adapter converter that can be used
        converterAdapter = JsonKGAdapter.create()
        isDebug = true
    }.createService<PhotoService>()

    private val mainRepository = MainRepository(service)

    val mainViewModelFactory = MainViewModelFactory(mainRepository)
}