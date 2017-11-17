package ru.nitrodenov.messenger.module.channels.interactor

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.nitrodenov.messenger.async.AsyncHandler
import ru.nitrodenov.messenger.async.PendingTask
import ru.nitrodenov.messenger.async.TaskParams
import ru.nitrodenov.messenger.async.TaskResult
import ru.nitrodenov.messenger.http.HttpConnection
import ru.nitrodenov.messenger.module.channels.ChannelsDataCallback
import ru.nitrodenov.messenger.module.channels.entity.ChannelsData
import ru.nitrodenov.messenger.module.channels.entity.ShortChannelData
import java.io.BufferedReader
import java.io.IOException
import java.lang.ref.WeakReference
import java.net.HttpURLConnection


interface ChannelsDataInteractor {

    fun loadChannelsData(id: String, callback: ChannelsDataCallback)

    fun stopTask(id: String)

}

class ChannelsDataInteractorImpl(private val asyncHandler: AsyncHandler,
                                 private val httpConnection: HttpConnection) : ChannelsDataInteractor {

    override fun loadChannelsData(id: String, callback: ChannelsDataCallback) {
        val channelsDataTask = ChannelsDataTask(callback, httpConnection)
        asyncHandler.submit(id, channelsDataTask)
    }

    override fun stopTask(id: String) {
        asyncHandler.stopTask(id)
    }

}

class ChannelsDataTask(callback: ChannelsDataCallback?,
                       private val httpConnection: HttpConnection) : PendingTask() {

    private val channelsDataCallback = WeakReference<ChannelsDataCallback>(callback)

    override fun doInBackground(vararg params: TaskParams?): TaskResult =
            ChannelsData(loadShortChannelData() ?: ArrayList())

    override fun onPostExecute(result: TaskResult?) {
        super.onPostExecute(result)

        val channelsData = result as ChannelsData

        val callback = channelsDataCallback.get()
        callback?.onChannelsDataLoaded(channelsData)
    }

    private fun loadShortChannelData(): List<ShortChannelData>? {
        try {
            val connection = httpConnection.getHttpConnection("http://10.10.10.71:8080/channels")
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

//todo move to file
inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

