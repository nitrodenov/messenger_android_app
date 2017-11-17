package ru.nitrodenov.messenger.module.channels.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.nitrodenov.messenger.R
import ru.nitrodenov.messenger.module.channel.entity.ChannelToolbarData
import ru.nitrodenov.messenger.module.channels.ChannelsItemClickListener
import ru.nitrodenov.messenger.module.channels.entity.ChannelsData
import ru.nitrodenov.messenger.module.common.ImagesCallback

class ChannelsAdapter(private val logosCallback: ImagesCallback,
                      private val itemClickListener: ChannelsItemClickListener) :
        RecyclerView.Adapter<ChannelsViewHolder>() {

    var channelsData: ChannelsData? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChannelsViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.channels_item, parent, false)
        val viewHolder = ChannelsViewHolder(view)
        view.setOnClickListener {
            val position = viewHolder.adapterPosition
            val channelData = channelsData?.channels?.get(position)
            val id = channelData?.id
            if (id != null) {
                val channelToolbarData = ChannelToolbarData(channelData.logos, channelData.title, channelData.description)
                itemClickListener.onItemClick(id, channelToolbarData, viewHolder.titleTextView, viewHolder.descriptionTextView)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ChannelsViewHolder?, position: Int) {
        holder?.titleTextView?.text = channelsData?.channels?.get(position)?.title
        holder?.descriptionTextView?.text = channelsData?.channels?.get(position)?.description
        holder?.timeTextView?.text = channelsData?.channels?.get(position)?.time

        holder?.logosMultiImageView?.setImageBitmap(null)
        logosCallback.loadLogos(holder?.logosMultiImageView!!,
                channelsData?.channels?.get(position)?.logos ?: ArrayList())
    }

    override fun getItemCount(): Int = channelsData?.channels?.size ?: 0
}