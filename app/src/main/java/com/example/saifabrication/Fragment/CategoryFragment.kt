package com.example.saifabrication.Fragment

import ImageAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saifabrication.R
import com.google.firebase.database.*

class CategoryFragment : Fragment() {

    private lateinit var galleryRecyclerView: RecyclerView
    private lateinit var buttonContainer: LinearLayout
    private var selectedButton: Button? = null
    private lateinit var databaseReference: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)

        galleryRecyclerView = view.findViewById(R.id.recycler_view_gallery)
        galleryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        buttonContainer = view.findViewById(R.id.button_container)
        setupCategoryButtons(view)

        // Initialize Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("Categories")

        return view
    }

    private fun setupCategoryButtons(view: View) {
        val buttons = listOf(
            view.findViewById<Button>(R.id.btn_windows),
            view.findViewById<Button>(R.id.btn_doors),
            view.findViewById<Button>(R.id.btn_gates),
            view.findViewById<Button>(R.id.btn_stairs)
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                highlightSelectedButton(button, buttons)
                alignButtonsToTop(buttons)
                fetchImagesFromFirebase(button.text.toString()) // Fetch images for the selected category
            }
        }
    }

    private fun highlightSelectedButton(button: Button, buttons: List<Button>) {
        buttons.forEach { it.setBackgroundResource(R.drawable.rounded_button) }
        button.setBackgroundResource(R.drawable.light_yellow_button)
        selectedButton = button
    }

    private fun alignButtonsToTop(buttons: List<Button>) {
        buttonContainer.orientation = LinearLayout.HORIZONTAL
        buttonContainer.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        buttons.forEach { button ->
            val params = button.layoutParams as LinearLayout.LayoutParams
            params.width = 0
            params.weight = 1f
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT
            params.marginStart = 8.dpToPx()
            params.marginEnd = 8.dpToPx()
            button.layoutParams = params
        }
    }

    private fun fetchImagesFromFirebase(category: String) {
        databaseReference.child(category).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val images = mutableListOf<String>()
                for (data in snapshot.children) {
                    val imageUrl = data.child("url").getValue(String::class.java)
                    imageUrl?.let { images.add(it) }
                }
                if (images.isEmpty()) {
                    Toast.makeText(context, "No images available for this category", Toast.LENGTH_SHORT).show()
                } else {
                    showGallery(images)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load images: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showGallery(images: List<String>) {
        galleryRecyclerView.visibility = View.VISIBLE
        galleryRecyclerView.adapter = ImageAdapter(images) { imageUrl ->
            showFullImageDialog(imageUrl) // Pass the image URL to the dialog
        }

        val params = galleryRecyclerView.layoutParams as LinearLayout.LayoutParams
        params.height = 0
        params.weight = 1f
        galleryRecyclerView.layoutParams = params
    }

    private fun showFullImageDialog(imageUrl: String) {
        val dialog = FullImageDialogFragment.newInstance(imageUrl) // Pass imageUrl using newInstance
        dialog.show(childFragmentManager, "FullImageDialogFragment")
    }

    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}
