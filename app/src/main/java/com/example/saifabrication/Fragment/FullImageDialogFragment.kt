package com.example.saifabrication.Fragment

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.saifabrication.R

class FullImageDialogFragment : DialogFragment() {

    private lateinit var imageName: String

    companion object {
        private const val ARG_IMAGE_NAME = "image_name"

        // Method to create an instance of FullImageDialogFragment with imageName as an argument
        fun newInstance(imageName: String): FullImageDialogFragment {
            val fragment = FullImageDialogFragment()
            val args = Bundle()
            args.putString(ARG_IMAGE_NAME, imageName)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_full_image, container, false)

        // Get the imageName from arguments
        imageName = arguments?.getString(ARG_IMAGE_NAME)
            ?: throw IllegalArgumentException("Image name not found in arguments")

        val imageView: ImageView = view.findViewById(R.id.full_image_view)
        val btnClose: Button = view.findViewById(R.id.btn_close)

        // Dynamically load the image using Glide
        val imageResourceId = requireContext().resources.getIdentifier(
            imageName,
            "drawable",
            requireContext().packageName
        )

        if (imageResourceId != 0) {
            Glide.with(requireContext())
                .load(imageResourceId)
                .fitCenter() // Adjust scaling
                .into(imageView)
        } else {
            Glide.with(requireContext())
                .load(R.drawable.placeholder)
                .fitCenter()
                .into(imageView)
        }

        // Set button actions
        btnClose.setOnClickListener { dismiss() }

        return view
    }
}
