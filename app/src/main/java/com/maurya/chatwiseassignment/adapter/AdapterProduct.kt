package com.maurya.chatwiseassignment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.maurya.chatwiseassignment.ProductDetailsActivity
import com.maurya.chatwiseassignment.R
import com.maurya.chatwiseassignment.databinding.ItemProductBinding
import com.maurya.chatwiseassignment.repository.Product

class AdapterProduct(
    private val context: Context,
    private var itemList: ArrayList<Product>
) :
    RecyclerView.Adapter<AdapterProduct.VideoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        return VideoHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {

        val currentItem = itemList[position]

        with(holder) {

            title.text = currentItem.title

            Glide.with(context)
                .asBitmap()
                .load(currentItem.thumbnail)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .error(R.drawable.icon_error)
                .into(thumbnail)

            holder.itemView.setOnClickListener {
                val intent = Intent(it.context, ProductDetailsActivity::class.java).apply {
                    putExtra("position", position)
                    putExtra("class", "Main")
                }
                it.context.startActivity(intent)
            }

        }


    }


    override fun getItemCount(): Int {
        return itemList.size
    }


    class VideoHolder(binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val thumbnail = binding.productThumbnail
        val root = binding.root


    }


}