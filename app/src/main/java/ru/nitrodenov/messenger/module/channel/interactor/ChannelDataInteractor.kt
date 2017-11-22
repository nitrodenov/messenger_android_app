package ru.nitrodenov.messenger.module.channel.interactor

import com.google.gson.Gson
import ru.nitrodenov.messenger.async.AsyncHandler
import ru.nitrodenov.messenger.async.PendingTask
import ru.nitrodenov.messenger.async.TaskParams
import ru.nitrodenov.messenger.async.TaskResult
import ru.nitrodenov.messenger.http.HttpConnection
import ru.nitrodenov.messenger.module.channel.ChannelDataCallback
import ru.nitrodenov.messenger.module.channel.entity.ChannelData
import ru.nitrodenov.messenger.module.channel.entity.Message
import ru.nitrodenov.messenger.module.channels.interactor.fromJson
import java.io.BufferedReader
import java.io.IOException
import java.lang.ref.WeakReference
import java.net.HttpURLConnection

interface ChannelDataInteractor {

    fun loadChannelData(id: String, lastItemPosition: Int, callback: ChannelDataCallback)

    fun stopTasks()

}

class ChannelDataInteractorImpl(private val asyncHandler: AsyncHandler,
                                private val httpConnection: HttpConnection) : ChannelDataInteractor {

    override fun loadChannelData(id: String, lastItemPosition: Int, callback: ChannelDataCallback) {
        val channelsDataTask = ChannelDataTask(callback, httpConnection, id, lastItemPosition)
        asyncHandler.submit(id, channelsDataTask)
    }

    override fun stopTasks() {
        asyncHandler.stopAllTasks()
    }

}

class ChannelDataTask(callback: ChannelDataCallback?,
                      private val httpConnection: HttpConnection,
                      private val id: String,
                      private val lastItemPosition: Int) : PendingTask() {

    private val channelDataCallback = WeakReference<ChannelDataCallback>(callback)

    override fun doInBackground(vararg params: TaskParams?): TaskResult =
            ChannelData(loadMessages(id, lastItemPosition) ?: ArrayList())

    override fun onPostExecute(result: TaskResult?) {
        super.onPostExecute(result)

        val channelsData = result as ChannelData

        val callback = channelDataCallback.get()
        callback?.onChannelDataLoaded(channelsData)
    }

    private fun loadMessages(id: String, lastItemPosition: Int): ArrayList<Message>? {
        try {
            val connection = httpConnection.getHttpConnection("http://10.10.10.18:8080/messages?id=$id&index=$lastItemPosition")
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK && !isCancelled) {
                val data = connection.inputStream.bufferedReader().use(BufferedReader::readText)
                return Gson().fromJson(data)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }
}