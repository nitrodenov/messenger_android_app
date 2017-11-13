package ru.nitrodenov.messenger.module.channel.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ru.nitrodenov.messenger.R

class IncomeMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val chatImageView: ImageView = view.findViewById(R.id.chat_image_view)
    val messageTextTiew: TextView = view.findViewById(R.id.message)
    val timeTextTiew: TextView = view.findViewById(R.id.time_text_view)
    val messageImageView: ImageView = view.findViewById(R.id.message_image_view)

}