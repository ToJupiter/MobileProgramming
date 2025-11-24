package com.example.baitapso9

// AppHorizontalAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppHorizontalAdapter(
    private val apps: List<StoreItem>,
    private val onAppClick: (StoreItem) -> Unit
) : RecyclerView.Adapter<AppHorizontalAdapter.AppHorizontalViewHolder>() {

    class AppHorizontalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appIconImageView: ImageView = itemView.findViewById(R.id.appIconImageView)
        val appTitleTextView: TextView = itemView.findViewById(R.id.appTitleTextView)
        val appSubtitleTextView: TextView = itemView.findViewById(R.id.appSubtitleTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppHorizontalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app_horizontal_card, parent, false)
        return AppHorizontalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppHorizontalViewHolder, position: Int) {
        val app = apps[position]

        holder.appTitleTextView.text = app.title
        holder.appSubtitleTextView.text = app.subtitle ?: "Developer"

        // In a real app, you would load the image using Coil or Glide
        // holder.appIconImageView.load(app.iconUrl)

        holder.itemView.setOnClickListener {
            onAppClick(app)
        }
    }

    override fun getItemCount(): Int = apps.size
}