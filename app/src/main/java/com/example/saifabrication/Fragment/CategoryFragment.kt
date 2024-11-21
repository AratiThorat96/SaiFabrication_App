package com.example.saifabrication.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saifabrication.ImageAdapter
import com.example.saifabrication.R

class CategoryFragment : Fragment() {

    private lateinit var galleryRecyclerView: RecyclerView
    private lateinit var buttonContainer: LinearLayout // Container for buttons
    private val imageMap = mapOf(
        "Windows" to listOf("win2", "banner1", "win3", "win4", "win5", "win6", "win7", "win8"),
        "Doors" to listOf("door1", "door2", "door3"),
        "Gates" to listOf("gate1", "gate2", "gate3"),
        "Stairs" to listOf("stair1", "stair2", "stair3")
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)

        galleryRecyclerView = view.findViewById(R.id.recycler_view_gallery)
        galleryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        buttonContainer = view.findViewById(R.id.button_container) // Get the container layout
        setupCategoryButtons(view)
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
                // Align buttons in a horizontal row at the top
                alignButtonsToTop(buttons)
                // Show gallery for the clicked category
                showGallery(button.text.toString())
            }
        }
    }

    private fun alignButtonsToTop(buttons: List<Button>) {
        // Change the layout of the button container to horizontal
        buttonContainer.orientation = LinearLayout.HORIZONTAL
        buttonContainer.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        buttons.forEach { button ->
            val params = button.layoutParams as LinearLayout.LayoutParams
            params.width = 0 // Distribute buttons evenly
            params.weight = 1f
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT
            params.marginStart = 8.dpToPx()
            params.marginEnd = 8.dpToPx()
            button.layoutParams = params
        }
    }

    private fun showGallery(category: String) {
        // Load images for the selected category into the RecyclerView
        val images = imageMap[category] ?: emptyList()
        galleryRecyclerView.visibility = View.VISIBLE
        galleryRecyclerView.adapter = ImageAdapter(images) { imageName ->
            showFullImageDialog(imageName) // Pass the image name to the dialog
        }

        // Set RecyclerView to fill the remaining space
        val params = galleryRecyclerView.layoutParams as LinearLayout.LayoutParams
        params.height = 0
        params.weight = 1f
        galleryRecyclerView.layoutParams = params
    }

    private fun showFullImageDialog(imageName: String) {
        val dialog = FullImageDialogFragment(imageName) { isSave, image ->
            if (isSave as Boolean) {
                // Handle the saved image
            }
        }
        dialog.show(childFragmentManager, "FullImageDialogFragment")
    }

    // Extension function to convert dp to px
    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}
