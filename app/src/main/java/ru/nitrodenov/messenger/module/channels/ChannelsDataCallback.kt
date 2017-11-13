package ru.nitrodenov.messenger.module.channels

import ru.nitrodenov.messenger.module.channels.entity.ChannelsData

interface ChannelsDataCallback {

    fun onChannelsDataLoaded(channelsData: ChannelsData)

}