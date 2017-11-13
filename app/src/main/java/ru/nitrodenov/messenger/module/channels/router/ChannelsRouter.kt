package ru.nitrodenov.messenger.module.channels.router

import android.view.View
import ru.nitrodenov.messenger.module.channel.entity.ChannelToolbarData

interface ChannelsRouter {

    fun goToChannel(id: String, channelToolbarData: ChannelToolbarData, titleTextView: View, descriptionTextView: View)

}