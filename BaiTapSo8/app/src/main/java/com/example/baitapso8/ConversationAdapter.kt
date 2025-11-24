package com.example.baitapso8

// ConversationAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ConversationAdapter(
    private var conversations: List<Conversation>,
    private val onItemClick: (Conversation) -> Unit,
    private val onSenderImageClick: (Conversation) -> Unit
) : RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>() {

    class ConversationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val senderImageView: ImageView = itemView.findViewById(R.id.senderImageView)
        val senderNameTextView: TextView = itemView.findViewById(R.id.senderNameTextView)
        val subjectTextView: TextView = itemView.findViewById(R.id.subjectTextView)
        val previewTextView: TextView = itemView.findViewById(R.id.previewTextView)
        val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_conversation, parent, false)
        return ConversationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        val conversation = conversations[position]

        holder.senderNameTextView.text = conversation.senderName
        holder.subjectTextView.text = conversation.subject
        holder.previewTextView.text = conversation.preview
        holder.timestampTextView.text = conversation.timestamp

        // Set unread status styling
        if (conversation.isUnread) {
            holder.senderNameTextView.setTypeface(null, android.graphics.Typeface.BOLD)
        } else {
            holder.senderNameTextView.setTypeface(null, android.graphics.Typeface.NORMAL)
        }

        // Set click listeners
        holder.itemView.setOnClickListener {
            onItemClick(conversation)
        }

        holder.senderImageView.setOnClickListener {
            onSenderImageClick(conversation)
        }
    }

    override fun getItemCount(): Int = conversations.size

    fun updateConversations(newConversations: List<Conversation>) {
        conversations = newConversations
        notifyDataSetChanged()
    }
}