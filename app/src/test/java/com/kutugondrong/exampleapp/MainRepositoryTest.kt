package com.kutugondrong.exampleapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kutugondrong.exampleapp.network.PhotoResponse
import com.kutugondrong.exampleapp.network.PhotoService
import com.kutugondrong.exampleapp.screen.main.MainRepository
import com.kutugondrong.networkkg.callback.CallbackKG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


/**
 * Unit Test for MainRepository
 * @see MainRepository
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainRepositoryTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var service: PhotoService

    @Before
    fun setUp() {
    }

    @Test
    fun `given Resource success when fetch should return success`() =
        testCoroutineRule.runBlockingTest {
            val callbackKG = CallbackKG<List<PhotoResponse>>()
            callbackKG.success = true
            callbackKG.message = null
            callbackKG.data = emptyList()
            `when`(service.getPhotos()).thenReturn(callbackKG)
            val repository = MainRepository(service)
            val result = repository.getPhotos()
            Assert.assertEquals(true, result?.success)

            val listExpected = emptyList<PhotoResponse>()

            Assert.assertEquals(listExpected, result?.data)
            Assert.assertEquals(null, result?.message)
            Assert.assertEquals(true, result?.success)
        }

    @Test
    fun `given Resource error when fetch should return failed`() =
        testCoroutineRule.runBlockingTest {
            val callbackKG = CallbackKG<List<PhotoResponse>>()
            callbackKG.success = false
            callbackKG.message = "Failed Test"
            callbackKG.data = null
            `when`(service.getPhotos()).thenReturn(callbackKG)
            val repository = MainRepository(service)
            val result = repository.getPhotos()

            val listExpected = null

            Assert.assertEquals(listExpected, result?.data)
            Assert.assertEquals("Failed Test", result?.message)
            Assert.assertEquals(false, result?.success)
        }

}