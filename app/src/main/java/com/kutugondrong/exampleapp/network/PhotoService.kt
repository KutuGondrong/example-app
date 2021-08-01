package com.kutugondrong.exampleapp.network

import com.kutugondrong.networkkg.annotation.Network
import com.kutugondrong.networkkg.annotation.ReturnTypeKG
import com.kutugondrong.networkkg.callback.CallbackKG
import com.kutugondrong.networkkg.collection.NetworkType
import com.kutugondrong.networkkg.NetworkKG


/**
 * PhotoService has implement by NetworkKg
 * @see NetworkKG
 */

interface PhotoService {
    @ReturnTypeKG(PhotoResponse::class, true)
    @Network(NetworkType.GET, "/photos")
    suspend fun getPhotos(): CallbackKG<List<PhotoResponse>>?
}