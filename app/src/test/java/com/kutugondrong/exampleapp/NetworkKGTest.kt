package com.kutugondrong.exampleapp

import com.kutugondrong.exampleapp.network.PhotoService
import com.kutugondrong.httpclientkg.HttpClientKG
import com.kutugondrong.jsonkgadapter.JsonKGAdapter
import com.kutugondrong.networkkg.NetworkKG
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

/**
 * Unit Test for NetworkKG
 * @see NetworkKG
 */
class NetworkKGTest {

    @Test
    fun `test success networkKG to get response`() = runBlocking {
        val service = NetworkKG.dslNetworkKG{
            httpClient = HttpClientKG.dslDefaultHttpClient {
                baseUrl = BuildConfig.SERVER_BASE_URL
                properties {
                    property {
                        key = "Authorization"
                        value = "Client-ID ${BuildConfig.API_KEY}"
                    }
                }
            }
            converterAdapter = JsonKGAdapter.create()
        }.createService<PhotoService>()

        val data = service.getPhotos()
        Assert.assertEquals(true, data?.success)
    }

    @Test
    fun `test failed networkKG get response because empty authorization`() = runBlocking {
        val service = NetworkKG.dslNetworkKG{
            httpClient = HttpClientKG.dslDefaultHttpClient {
                baseUrl = BuildConfig.SERVER_BASE_URL
                properties {
                    property {
                        key = "Authorization"
                        value = ""
                    }
                }
            }
            converterAdapter = JsonKGAdapter.create()
        }.createService<PhotoService>()

        val data = service.getPhotos()
        Assert.assertEquals(false, data?.success)
    }
}