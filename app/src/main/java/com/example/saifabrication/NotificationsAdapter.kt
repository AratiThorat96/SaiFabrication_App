package com.example.saifabrication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class NotificationsAdapter(private val notifications: List<Notification>) :
    RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]
        holder.bind(notification)
    }

    override fun getItemCount(): Int = notifications.size

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.tvMessage)
        private val timestampTextView: TextView = itemView.findViewById(R.id.tvTimestamp)

        fun bind(notification: Notification) {
            messageTextView.text = notification.message

            // Format timestamp
            val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
            val formattedTime = sdf.format(Date(notification.timestamp.toLong()))
            timestampTextView.text = formattedTime
        }
    }
}
