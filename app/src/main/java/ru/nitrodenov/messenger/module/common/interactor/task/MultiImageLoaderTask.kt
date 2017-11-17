package ru.nitrodenov.messenger.module.common.interactor.task

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import ru.nitrodenov.messenger.ImageCache
import ru.nitrodenov.messenger.async.PendingTask
import ru.nitrodenov.messenger.async.TaskParams
import ru.nitrodenov.messenger.async.TaskResult
import ru.nitrodenov.messenger.async.TaskResultCallback
import ru.nitrodenov.messenger.http.HttpConnection
import ru.nitrodenov.messenger.module.channels.entity.Logos
import java.io.IOException
import java.lang.ref.WeakReference
import java.net.HttpURLConnection

class MultiImageLoaderTask(val urls: List<String>,
                           imageView: ImageView,
                           private val httpConnection: HttpConnection,
                           private val imageCache: ImageCache,
                           private val resultCallback: TaskResultCallback) : PendingTask() {

    private val weakImageView = WeakReference<ImageView>(imageView)

    override fun doInBackground(vararg params: TaskParams?): TaskResult {
        val logos = ArrayList<Bitmap>()
        for (url in urls) {
            var bitmap: Bitmap? = null
            val urlHash = url.hashCode().toString()

            if (!isCancelled && getAttachedImageView() != null) {
                bitmap = imageCache.getBitmapFromDiskCache(urlHash)
            }

            if (!isCancelled && getAttachedImageView() != null) {
                if (bitmap != null) {
                    imageCache.addBitmapToMemoryCache(urlHash, bitmap)
                    logos.add(bitmap)
                } else {
                    Log.d(TAG, "start loading $url in ${weakImageView.get()?.hashCode()}")
                    bitmap = downloadImage(url)

                    if (bitmap != null && !isCancelled && getAttachedImageView() != null) {
                        imageCache.addBitmapToDiskCache(urlHash, bitmap)
                        imageCache.addBitmapToMemoryCache(urlHash, bitmap)
                        logos.add(bitmap)
                    }
                }
            }
        }

        return Logos(logos)
    }

    override fun onPostExecute(result: TaskResult?) {
        super.onPostExecute(result)

        val imageView = getAttachedImageView()
        val logos = (result as? Logos)?.logos

        if (logos != null && !logos.isEmpty() && imageView != null) {
            for (logo in logos) {
                Log.d(TAG, "add in ${weakImageView.get()?.hashCode()}")
                imageView.setImageBitmap(logo)
            }
        }

        val taskId = getAttachedImageView()?.hashCode()?.toString()
        if (taskId != null) {
            resultCallback.onLoaded(taskId)
        }
    }

    private fun getAttachedImageView(): ImageView? = weakImageView.get()

    private fun downloadImage(url: String): Bitmap? {
        try {
            val connection = httpConnection.getHttpConnection(url)
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK && !isCancelled) {
                return BitmapFactory.decodeStream(connection.inputStream)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }
}

private const val TAG = "MultiImageLoaderTask"