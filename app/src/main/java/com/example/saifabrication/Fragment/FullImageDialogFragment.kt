package com.example.saifabrication.Fragment

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.saifabrication.R
import com.example.saifabrication.ViewModel.SharedViewModel

class FullImageDialogFragment(private val imageName: String, param: (Any, Any) -> Unit) : DialogFragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_full_image, container, false)

        val imageView: ImageView = view.findViewById(R.id.full_image_view)
        val btnClose: Button = view.findViewById(R.id.btn_close)
        val btnSave: Button = view.findViewById(R.id.btn_save)

        // Dynamically load the image using the imageName
        val imageResourceId = requireContext().resources.getIdentifier(imageName, "drawable", requireContext().packageName)
        if (imageResourceId != 0) {
            imageView.setImageResource(imageResourceId)
        } else {
            imageView.setImageResource(R.drawable.placeholder)
        }

        // Set button actions
        btnClose.setOnClickListener { dismiss() }
        btnSave.setOnClickListener {
            sharedViewModel.addImage(imageName) // Save the image to ViewModel
            dismiss()
        }

        return view
    }
}
