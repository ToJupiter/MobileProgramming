package com.example.baitapso9

// AppVerticalAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppVerticalAdapter(
    private val apps: List<StoreItem>,
    private val onAppClick: (StoreItem) -> Unit
) : RecyclerView.Adapter<AppVerticalAdapter.AppVerticalViewHolder>() {

    class AppVerticalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.appTitleTextView)
        val genresTextView: TextView = itemView.findViewById(R.id.appGenresTextView)
        val ratingTextView: TextView = itemView.findViewById(R.id.ratingTextView)
        val sizeTextView: TextView = itemView.findViewById(R.id.sizeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppVerticalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app_vertical, parent, false)
        return AppVerticalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppVerticalViewHolder, position: Int) {
        val app = apps[position]

        holder.titleTextView.text = app.title
        holder.genresTextView.text = app.genres?.joinToString(" • ") ?: ""
        holder.ratingTextView.text = app.rating?.toString() ?: ""
        holder.sizeTextView.text = app.size ?: ""

        holder.itemView.setOnClickListener {
            onAppClick(app)
        }
    }

    override fun getItemCount(): Int = apps.size
}