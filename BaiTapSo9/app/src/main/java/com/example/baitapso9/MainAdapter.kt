package com.example.baitapso9

// MainAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(
    private val items: List<MainListItem>,
    private val onAppClick: (StoreItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_DIVIDER = 1
        const val VIEW_TYPE_APP_SECTION = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is MainListItem.SectionHeaderItem -> VIEW_TYPE_HEADER
            is MainListItem.DividerItem -> VIEW_TYPE_DIVIDER
            is MainListItem.AppListSectionItem -> VIEW_TYPE_APP_SECTION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_section_header, parent, false)
                SectionHeaderViewHolder(view)
            }
            VIEW_TYPE_DIVIDER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_divider, parent, false)
                DividerViewHolder(view)
            }
            VIEW_TYPE_APP_SECTION -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_app_list_section, parent, false)
                AppSectionViewHolder(view, onAppClick)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SectionHeaderViewHolder -> {
                val item = items[position] as MainListItem.SectionHeaderItem
                holder.bind(item.title)
            }
            is DividerViewHolder -> {
                // No binding needed for divider
            }
            is AppSectionViewHolder -> {
                val item = items[position] as MainListItem.AppListSectionItem
                holder.bind(item)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class SectionHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.sectionTitleTextView)

        fun bind(title: String) {
            titleTextView.text = title
        }
    }

    class DividerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class AppSectionViewHolder(
        itemView: View,
        private val onAppClick: (StoreItem) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val sectionTitleTextView: TextView = itemView.findViewById(R.id.sectionTitleTextView)
        private val appsRecyclerView: RecyclerView = itemView.findViewById(R.id.appsRecyclerView)

        fun bind(item: MainListItem.AppListSectionItem) {
            sectionTitleTextView.text = item.sectionTitle

            appsRecyclerView.apply {
                layoutManager = if (item.isHorizontal) {
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                } else {
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }

                val adapter = if (item.isHorizontal) {
                    AppHorizontalAdapter(item.apps, onAppClick)
                } else {
                    AppVerticalAdapter(item.apps, onAppClick)
                }
                this.adapter = adapter
            }
        }
    }
}