package com.kutugondrong.exampleapp.screen.main

import com.kutugondrong.exampleapp.network.PhotoResponse
import com.kutugondrong.exampleapp.network.PhotoService
import com.kutugondrong.networkkg.callback.CallbackKG

/**
 * KG KutuGondrong
 * MainRepository is repository for MainViewModel
 * @see MainViewModel
 * Service for this repository using PhotoService
 * @see PhotoService
 */
class MainRepository(private val service: PhotoService) {

    suspend fun getPhotos(): CallbackKG<List<PhotoResponse>>? {
        return service.getPhotos()
    }

}