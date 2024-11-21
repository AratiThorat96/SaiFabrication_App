package com.example.saifabrication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.saifabrication.R

class ImageAdapter(
    private val images: List<String>,
    private val onImageClick: (String) -> Unit // Callback when an image is clicked
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageName = images[position]
        val imageResourceId = holder.itemView.context.resources.getIdentifier(imageName, "drawable", holder.itemView.context.packageName)
        holder.imageView.setImageResource(imageResourceId) // Set the image dynamically

        holder.itemView.setOnClickListener {
            onImageClick(imageName) // Pass the image name when clicked
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
    }
}
