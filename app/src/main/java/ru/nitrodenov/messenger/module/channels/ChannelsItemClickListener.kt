package ru.nitrodenov.messenger.module.channels

import android.view.View
import ru.nitrodenov.messenger.module.channel.entity.ChannelToolbarData

interface ChannelsItemClickListener {

    fun onItemClick(id: String, channelToolbarData: ChannelToolbarData, titleTextView: View, descriptionTextView: View)

}