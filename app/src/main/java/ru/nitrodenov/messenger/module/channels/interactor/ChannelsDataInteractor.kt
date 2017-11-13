package ru.nitrodenov.messenger.module.channels.interactor

import ru.nitrodenov.messenger.async.PendingTask
import ru.nitrodenov.messenger.async.TaskParams
import ru.nitrodenov.messenger.async.TaskResult
import ru.nitrodenov.messenger.async.AsyncHandler
import ru.nitrodenov.messenger.module.channels.ChannelsDataCallback
import ru.nitrodenov.messenger.module.channels.entity.ChannelsData
import ru.nitrodenov.messenger.module.channels.entity.createMock
import java.lang.ref.WeakReference

interface ChannelsDataInteractor {

    fun loadChannelsData(id: String, callback: ChannelsDataCallback)

    fun stopTask(id: String)

}

class ChannelsDataInteractorImpl(private val asyncHandler: AsyncHandler) : ChannelsDataInteractor {

    override fun loadChannelsData(id: String, callback: ChannelsDataCallback) {
        val channelsDataTask = ChannelsDataTask(callback)
        asyncHandler.submit(id, channelsDataTask)
    }

    override fun stopTask(id: String) {
        asyncHandler.stopTask(id)
    }

}

class ChannelsDataTask(callback: ChannelsDataCallback?) : PendingTask() {

    private val channelsDataCallback = WeakReference<ChannelsDataCallback>(callback)

    override fun doInBackground(vararg params: TaskParams?): TaskResult {
        Thread.sleep(2000)

        return createMock()
    }

    override fun onPostExecute(result: TaskResult?) {
        super.onPostExecute(result)

        val channelsData = result as ChannelsData

        val callback = channelsDataCallback.get()
        callback?.onChannelsDataLoaded(channelsData)
    }
}