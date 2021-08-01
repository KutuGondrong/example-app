package com.kutugondrong.exampleapp.screen.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.kutugondrong.exampleapp.MyApplication
import com.kutugondrong.exampleapp.R
import com.kutugondrong.exampleapp.adapter.PhotoRecyclerViewAdapter
import com.kutugondrong.exampleapp.databinding.ActivityMainBinding
import com.kutugondrong.exampleapp.network.PhotoResponse
import com.kutugondrong.exampleapp.network.helper.Resource
import com.kutugondrong.exampleapp.network.helper.Status
import com.kutugondrong.imagekglibrary.ZoomImageKGViewShowFromURL
import java.lang.reflect.*


/**
 * KG KutuGondrong
 * MainActivity is class default launcher Application
 * Using view model
 * @see MainViewModel
 */

class MainActivity : AppCompatActivity(),
    PhotoRecyclerViewAdapter.PhotoRecyclerViewAdapterListener {

    companion object {
        const val SPAN_COUNT_ONE = 1
        const val SPAN_COUNT_TWO = 2
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterPhoto: PhotoRecyclerViewAdapter
    private lateinit var layoutManagerPhoto: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val appContainer = (application as MyApplication).appContainer
        viewModel = appContainer.mainViewModelFactory.create()
        viewModel.getPhotos()
        with(binding){
            with(list){
                /**
                 * Initiate Adapter for RecyclerView of MainActivity
                 * @see PhotoRecyclerViewAdapter
                 */
                adapterPhoto = PhotoRecyclerViewAdapter(this@MainActivity)
                layoutManagerPhoto = GridLayoutManager(context, SPAN_COUNT_ONE)
                layoutManager = layoutManagerPhoto
                adapter = adapterPhoto
            }

            // Create the observer which updates the UI.
            val nameObserver = Observer<Resource<List<PhotoResponse>>> { response ->
                when (response.status) {
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        btnTryAgain.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        btnTryAgain.visibility = View.GONE
                        response.data?.let {
                            adapterPhoto.setItems(it)
                        }
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        btnTryAgain.visibility = View.VISIBLE
                    }
                }
            }

            btnTryAgain.setOnClickListener{
                viewModel.getPhotos()
            }

            // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
            viewModel.photos.observe(this@MainActivity, nameObserver)
        }

    }

    private fun switchLayout(){
        if(layoutManagerPhoto.spanCount == SPAN_COUNT_ONE) {
            layoutManagerPhoto.spanCount = SPAN_COUNT_TWO
            adapterPhoto.setViewType(PhotoRecyclerViewAdapter.VIEW_TYPE_GRID)
        } else {
            layoutManagerPhoto.spanCount = SPAN_COUNT_ONE
            adapterPhoto.setViewType(PhotoRecyclerViewAdapter.VIEW_TYPE_LIST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                val searchView = item.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        viewModel.queryData(query)
                        return false
                    }
                    override fun onQueryTextChange(newText: String): Boolean {
                        viewModel.queryData(newText)
                        return false
                    }
                })
            }
            R.id.action_switch -> {
                switchLayout()
                if(layoutManagerPhoto.spanCount == SPAN_COUNT_ONE) {
                    item.setIcon(android.R.drawable.ic_menu_report_image)
                } else {
                    item.setIcon(android.R.drawable.picture_frame)
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * This method for show image as full screen
     * Using ZoomImageKGViewShowFromURL
     * @see ZoomImageKGViewShowFromURL
     */
    override fun onClickPhotoRecyclerViewAdapter(urlImage: String) {
        val intent = Intent(this, ZoomImageKGViewShowFromURL::class.java)
        intent.putExtra(ZoomImageKGViewShowFromURL.URL_IMAGE, urlImage)
        startActivity(intent)
    }

}
