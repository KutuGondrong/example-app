package com.kutugondrong.exampleapp.network

import com.kutugondrong.jsonkg.SerializedName
import com.kutugondrong.jsonkg.SpecialSerializedName

data class PhotoResponse(
    @SerializedName("alt_description")
    val description: String,
    var user: User,
)

data class User(
    var name: String,
    @SpecialSerializedName("profile_image", "medium")
    var image: String
)
