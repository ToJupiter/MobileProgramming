package com.example.baitapso8

// Conversation.kt
data class Conversation(
    val senderId: String,
    val senderName: String,
    val senderImageUrl: String?,
    val subject: String,
    val preview: String,
    val timestamp: String? = null,
    val isUnread: Boolean = false
)