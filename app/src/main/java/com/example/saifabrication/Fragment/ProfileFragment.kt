package com.example.saifabrication.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.saifabrication.R
import com.example.saifabrication.databinding.FragmentProfileBinding
import com.example.saifabrication.model.UserModel
import com.example.saifabrication.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        setUserData()

        // Save Info Button Click
        binding.saveinfobutton.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val address = binding.address.text.toString().trim()
            val phone = binding.phone.text.toString().trim()

            // Validate inputs
            if (validateInputs(email, phone)) {
                updateUserData(name, email, address, phone)
            }
        }

        // Log Out Button Click
        binding.logoutButton.setOnClickListener {
            logoutUser()
        }

        return binding.root
    }

    private fun validateInputs(email: String, phone: String): Boolean {
        // Check phone number
        if (phone.length != 10 || !phone.all { it.isDigit() }) {
            Toast.makeText(requireContext(), "Enter a valid 10-digit phone number", Toast.LENGTH_SHORT).show()
            return false
        }

        // Check email format
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(requireContext(), "Enter a valid email address", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun logoutUser() {
        auth.signOut() // Sign out the user
        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()

        // Navigate to LoginActivity
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clear activity stack
        startActivity(intent)
    }

    private fun updateUserData(name: String, email: String, address: String, phone: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userReference = database.getReference("user").child(userId)
            val userData = hashMapOf(
                "name" to name,
                "address" to address,
                "email" to email,
                "phone" to phone
            )
            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Profile Update Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUserData() {
        val userId = auth.currentUser
        if (userId != null) {
            val userReference = database.getReference("user").child(userId.uid)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userProfile = snapshot.getValue(UserModel::class.java)
                        if (userProfile != null) {
                            binding.name.setText(userProfile.userName)
                            binding.address.setText(userProfile.address)
                            binding.email.setText(userProfile.email)
                            binding.phone.setText(userProfile.phone)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Failed to fetch user data", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    companion object {

    }
}
