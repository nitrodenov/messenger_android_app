package ru.nitrodenov.messenger.module.channel.interactor

import ru.nitrodenov.messenger.async.PendingTask
import ru.nitrodenov.messenger.async.TaskParams
import ru.nitrodenov.messenger.async.TaskResult
import ru.nitrodenov.messenger.async.AsyncHandler
import ru.nitrodenov.messenger.module.channel.ChannelDataCallback
import ru.nitrodenov.messenger.module.channel.entity.ChannelData
import ru.nitrodenov.messenger.module.channel.entity.createMock
import java.lang.ref.WeakReference

interface ChannelDataInteractor {

    fun loadChannelData(id: String, callback: ChannelDataCallback)

}

class ChannelDataInteractorImpl(private val asyncHandler: AsyncHandler) : ChannelDataInteractor {

    override fun loadChannelData(id: String, callback: ChannelDataCallback) {
        val channelsDataTask = ChannelDataTask(callback)
        asyncHandler.submit(id, channelsDataTask)
    }

}

class ChannelDataTask(callback: ChannelDataCallback?) : PendingTask() {

    private val channelDataCallback = WeakReference<ChannelDataCallback>(callback)

    override fun doInBackground(vararg params: TaskParams?): TaskResult {
        Thread.sleep(2000)

        return createMock()
    }

    override fun onPostExecute(result: TaskResult?) {
        super.onPostExecute(result)

        val channelsData = result as ChannelData

        val callback = channelDataCallback.get()
        callback?.onChannelDataLoaded(channelsData)
    }
}