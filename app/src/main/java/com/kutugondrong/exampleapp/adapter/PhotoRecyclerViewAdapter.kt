package com.kutugondrong.exampleapp.adapter

import com.kutugondrong.exampleapp.screen.main.MainActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kutugondrong.exampleapp.databinding.AdapterPhotoItemTypeGridBinding
import com.kutugondrong.exampleapp.databinding.AdapterPhotoItemTypeListBinding
import com.kutugondrong.exampleapp.network.PhotoResponse
import com.kutugondrong.imagekglibrary.ImageKG

/**
 * KG KutuGondrong
 * PhotoRecyclerViewAdapter to create item list view in MainActivity
 * How it is used can be seen here
 * @see PhotoRecyclerViewAdapter.ViewHolderList.bind
 * @see PhotoRecyclerViewAdapter.ViewHolderGrid.bind
 *
 *
 *
 * @see MainActivity
 */
class PhotoRecyclerViewAdapter(
    private val listener: PhotoRecyclerViewAdapterListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemPhoto: List<PhotoResponse> = ArrayList()

    companion object {
        const val VIEW_TYPE_LIST = 1
        const val VIEW_TYPE_GRID = 2
    }

    private var mViewType = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            VIEW_TYPE_LIST -> {
                return ViewHolderList(
                    AdapterPhotoItemTypeListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                return ViewHolderGrid(
                    AdapterPhotoItemTypeGridBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (mViewType == VIEW_TYPE_LIST) {
            val holderList = (holder as ViewHolderList)
            holderList.bind(position)
        } else {
            val holderGrid = (holder as ViewHolderGrid)
            holderGrid.bind(position)
        }
    }

    override fun getItemCount(): Int = itemPhoto.size

    /**
     * VieHolder Type List
     */
    inner class ViewHolderList(binding: AdapterPhotoItemTypeListBinding) : RecyclerView.ViewHolder(binding.root) {
        private val txtName: TextView = binding.txtName
        private val txtDesc: TextView = binding.txtDesc
        private val imageView: ImageView = binding.imageView

        fun bind(position: Int) {
            val item = itemPhoto[position]
            txtName.text = item.user.name
            txtDesc.text = item.description
            ImageKG.dslImageHelper {
                context = itemView.context
                urlImage = item.user.image
                this.imageView = this@ViewHolderList.imageView
            }.process()
            itemView.setOnClickListener{
                listener.onClickPhotoRecyclerViewAdapter(item.user.image)
            }
        }
    }


    /**
     * VieHolder Type Grid
     */
    inner class ViewHolderGrid(binding: AdapterPhotoItemTypeGridBinding) : RecyclerView.ViewHolder(binding.root) {
        private val txtName: TextView = binding.txtName
        private val txtDesc: TextView = binding.txtDesc
        private val imageView: ImageView = binding.imageView

        fun bind(position: Int) {
            val item = itemPhoto[position]
            txtName.text = item.user.name
            txtDesc.text = item.description
            ImageKG.dslImageHelper {
                context = itemView.context
                urlImage = item.user.image
                this.imageView = this@ViewHolderGrid.imageView
            }.process()
            itemView.setOnClickListener{
                listener.onClickPhotoRecyclerViewAdapter(item.user.image)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mViewType
    }

    fun setViewType(type: Int) {
        mViewType = type
        if (itemPhoto.isNotEmpty()) {
            notifyItemRangeChanged(0, itemCount)
        }
    }

    fun setItems(items: List<PhotoResponse>) {
        itemPhoto = items
        notifyDataSetChanged()
    }

    interface PhotoRecyclerViewAdapterListener {
        fun onClickPhotoRecyclerViewAdapter(urlImage: String)
    }

}

