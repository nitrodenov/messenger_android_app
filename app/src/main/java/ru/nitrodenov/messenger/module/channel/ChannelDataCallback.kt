package ru.nitrodenov.messenger.module.channel

import ru.nitrodenov.messenger.module.channel.entity.ChannelData

interface ChannelDataCallback {

    fun onChannelDataLoaded(channelData: ChannelData)

}