package com.example.saifabrication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class NotificationActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationsAdapter
    private val notificationsList = mutableListOf<Notification>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        // Close Button Logic
        val closeButton = findViewById<ImageView>(R.id.closeButton)
        closeButton.setOnClickListener {
            finish() // Closes the current activity
        }

        // Setup RecyclerView
        recyclerView = findViewById(R.id.rvNotifications)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NotificationsAdapter(notificationsList)
        recyclerView.adapter = adapter

        // Fetch notifications from Firebase
        val databaseRef = FirebaseDatabase.getInstance().getReference("Notifications")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                notificationsList.clear()
                for (notificationSnapshot in snapshot.children) {
                    val notification = notificationSnapshot.getValue(Notification::class.java)
                    if (notification != null) {
                        notificationsList.add(notification)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@NotificationActivity,
                    "Failed to load notifications",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
