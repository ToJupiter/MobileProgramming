package com.example.baitapso8

// MainActivity.kt
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ConversationAdapter
    private val conversations = mutableListOf<Conversation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        setupRecyclerView()
        loadSampleData()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.conversationRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ConversationAdapter(
            conversations,
            onItemClick = { conversation ->
                // Handle conversation item click
                Toast.makeText(this, "Opened conversation with ${conversation.senderName}", Toast.LENGTH_SHORT).show()
            },
            onSenderImageClick = { conversation ->
                // Handle sender image click - selection
                Toast.makeText(this, "Selected conversation with ${conversation.senderName}", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.adapter = adapter
    }

    private fun loadSampleData() {
        // Add sample conversations
        conversations.add(
            Conversation(
                senderId = "1",
                senderName = "Edurila.com",
                senderImageUrl = null,
                subject = "$19 Only (First 10 spots)...",
                preview = "Are you looking to Learn Web Designin...",
                timestamp = "9:41 AM",
                isUnread = true
            )
        )

        conversations.add(
            Conversation(
                senderId = "2",
                senderName = "Google",
                senderImageUrl = null,
                subject = "Security alert",
                preview = "We noticed a new sign-in to your Google Account...",
                timestamp = "8:30 AM",
                isUnread = true
            )
        )

        conversations.add(
            Conversation(
                senderId = "3",
                senderName = "GitHub",
                senderImageUrl = null,
                subject = "[GitHub] Your password has been changed",
                preview = "Your GitHub password was successfully changed...",
                timestamp = "Yesterday",
                isUnread = false
            )
        )

        conversations.add(
            Conversation(
                senderId = "4",
                senderName = "LinkedIn",
                senderImageUrl = null,
                subject = "Someone viewed your profile",
                preview = "You received 3 profile views today...",
                timestamp = "Nov 23",
                isUnread = false
            )
        )

        conversations.add(
            Conversation(
                senderId = "5",
                senderName = "Netflix",
                senderImageUrl = null,
                subject = "New on Netflix this week",
                preview = "Check out what's new and trending on Netflix...",
                timestamp = "Nov 22",
                isUnread = false
            )
        )

        conversations.add(
            Conversation(
                senderId = "6",
                senderName = "Netflix",
                senderImageUrl = null,
                subject = "Netflix Account Password",
                preview = "Change Password",
                timestamp = "Nov 23",
                isUnread = false
            )
        )

        conversations.add(
            Conversation(
                senderId = "7",
                senderName = "Microsoft",
                senderImageUrl = null,
                subject = "Microsoft Account Password",
                preview = "Change Password",
                timestamp = "Nov 24",
                isUnread = false
            )
        )

        conversations.add(
            Conversation(
                senderId = "8",
                senderName = "Google",
                senderImageUrl = null,
                subject = "Gmail Account Password",
                preview = "Change Password",
                timestamp = "Nov 25",
                isUnread = false
            )
        )

        adapter.updateConversations(conversations)
    }
}