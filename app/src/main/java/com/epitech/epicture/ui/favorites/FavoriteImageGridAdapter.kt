package com.epitech.epicture.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.epitech.epicture.databinding.ImageFavoriteBinding
import com.epitech.epicture.model.Image

enum class ImageType { UPLOAD, FAVORITE }

class FavoriteImageGridAdapter(/*val clickListener: ClickListener*/) :
    PagingDataAdapter<Image, FavoriteImageGridAdapter.ImageViewHolder>(PhotoDiffCallback) {
    class ImageViewHolder(private val binding: ImageFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {
            binding.innerImage.property = image
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ImageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ImageFavoriteBinding.inflate(layoutInflater, parent, false)

                return ImageViewHolder(binding)
            }
        }
    }

//    class ClickListener(val clickListener: (image: Image) -> Unit) {
//        fun onClick(image: Image) = clickListener(image)
//    }

    companion object PhotoDiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)

        item?.let { holder.bind(it) }
    }
}
