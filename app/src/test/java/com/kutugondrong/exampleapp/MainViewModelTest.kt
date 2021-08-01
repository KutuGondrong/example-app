package com.kutugondrong.exampleapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.kutugondrong.exampleapp.network.PhotoResponse
import com.kutugondrong.exampleapp.network.PhotoService
import com.kutugondrong.exampleapp.network.helper.Resource
import com.kutugondrong.exampleapp.screen.main.MainRepository
import com.kutugondrong.exampleapp.screen.main.MainViewModel
import com.kutugondrong.networkkg.callback.CallbackKG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


/**
 * Unit Test for MainViewModel
 * @see MainViewModel
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var service: PhotoService

    @Mock
    private lateinit var photosObserve: Observer<Resource<List<PhotoResponse>>>

    private val callbackKG = CallbackKG<List<PhotoResponse>>()

    @Before
    fun setUp() {
        callbackKG.data = ArrayList()
    }

    @Test
    fun `given Resource success when fetch should return success`() =
        testCoroutineRule.runBlockingTest {
            callbackKG.success = true
            callbackKG.message = null
            `when`(service.getPhotos()).thenReturn(callbackKG)
            val repository = MainRepository(service)
            val viewModel = MainViewModel(repository)
            viewModel.photos.observeForever(photosObserve)
            viewModel.getPhotos()
            verify(photosObserve).onChanged(Resource.success(emptyList()))
            viewModel.photos.removeObserver(photosObserve)
        }

    @Test
    fun `given Resource error when fetch should return failed`() =
        testCoroutineRule.runBlockingTest {
            callbackKG.success = false
            callbackKG.message = "Error Test"
            `when`(service.getPhotos()).thenReturn(callbackKG)
            val repository = MainRepository(service)
            val viewModel = MainViewModel(repository)
            viewModel.photos.observeForever(photosObserve)
            viewModel.getPhotos()
            verify(photosObserve).onChanged(Resource.error("Error Test"))
            viewModel.photos.removeObserver(photosObserve)
        }


}