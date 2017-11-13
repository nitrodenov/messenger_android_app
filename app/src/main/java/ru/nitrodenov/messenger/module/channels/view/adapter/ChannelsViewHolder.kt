package ru.nitrodenov.messenger.module.channels.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import ru.nitrodenov.messenger.R
import ru.nitrodenov.messenger.widgets.MultipleImageView

class ChannelsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val logosMultiImageView: MultipleImageView = view.findViewById(R.id.logos_multi_image_view)
    val titleTextView: TextView = view.findViewById(R.id.title_text_view)
    val timeTextView: TextView = view.findViewById(R.id.time_text_view)
    val descriptionTextView: TextView = view.findViewById(R.id.description_text_view)

}