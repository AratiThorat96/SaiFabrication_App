package com.example.saifabrication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class TopDesignsAdapter(
    private val context: Context,
    private val imageList: List<Int>
) : RecyclerView.Adapter<TopDesignsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.designImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageResId = imageList[position]

        // Set the image resource to the ImageView
        holder.imageView.setImageResource(imageResId)

        // Dummy data for additional details
        val designName = "Design ${position + 1}"
        val designMaterial = if (position % 2 == 0) "Iron" else "Steel"
        val designPrice = "₹${1000 + (position * 500)}"

        // Set click listener to navigate to DesignDetailsActivity
        holder.imageView.setOnClickListener {
            val intent = Intent(context, DesignDetailsActivity::class.java).apply {
                putExtra("imageResId", imageResId)
                putExtra("designName", designName)
                putExtra("designMaterial", designMaterial)
                putExtra("designPrice", designPrice)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = imageList.size
}
