package com.example.baitapso9

// Models.kt
data class StoreItem(
    val id: String,
    val title: String,
    val subtitle: String?,
    val iconUrl: String,
    val genres: List<String>?,
    val rating: Double?,
    val size: String?,
    val displayType: DisplayType
)

enum class DisplayType {
    VERTICAL_LIST,
    HORIZONTAL_CARD
}

sealed class MainListItem {
    data class SectionHeaderItem(val title: String) : MainListItem()
    data class AppListSectionItem(
        val sectionTitle: String,
        val apps: List<StoreItem>,
        val isHorizontal: Boolean
    ) : MainListItem()
    object DividerItem : MainListItem()
}