package com.example.saifabrication

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DesignDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_design_details)

        // Get data from the intent
        val imageResId = intent.getIntExtra("imageResId", 0)
        val designName = intent.getStringExtra("designName") ?: "Unknown Design"
        val designMaterial = intent.getStringExtra("designMaterial") ?: "Unknown Material"
        val designPrice = intent.getStringExtra("designPrice") ?: "Unknown Price"

        // Bind data to the views
        val imageView: ImageView = findViewById(R.id.detailImageView)
        val nameTextView: TextView = findViewById(R.id.designNameTextView)
        val materialTextView: TextView = findViewById(R.id.designMaterialTextView)
        val priceTextView: TextView = findViewById(R.id.designPriceTextView)
        val closeButton: ImageView = findViewById(R.id.closeButton) // Close button

        imageView.setImageResource(imageResId)
        nameTextView.text = designName
        materialTextView.text = designMaterial
        priceTextView.text = designPrice

        // Handle close button click
        closeButton.setOnClickListener {
            finish() // Close the current activity
        }
    }
}
