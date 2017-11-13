package ru.nitrodenov.messenger.module.common.interactor

import android.util.Log
import android.widget.ImageView
import ru.nitrodenov.messenger.ImageCache
import ru.nitrodenov.messenger.async.AsyncHandler
import ru.nitrodenov.messenger.async.TaskResultCallback
import ru.nitrodenov.messenger.http.HttpConnection
import ru.nitrodenov.messenger.module.common.interactor.task.MultiImageLoaderTask

interface MultiImageLoaderInteractor {

    fun loadImages(imageView: ImageView, imageUrls: List<String>)

}

class MultiImageLoaderInteractorImpl(private val asyncHandler: AsyncHandler,
                                     private val httpConnection: HttpConnection,
                                     private val imageCache: ImageCache) : MultiImageLoaderInteractor, TaskResultCallback {

    override fun loadImages(imageView: ImageView, imageUrls: List<String>) {
        if (imageUrls.isEmpty()) {
            return
        }

        val noCachedImageUrls = ArrayList<String>()
        for (url in imageUrls) {
            val urlHash = url.hashCode().toString()
            val image = imageCache.getBitmapFromMemoryCache(urlHash)
            if (image != null) {
                Log.d(TAG, "in memory $url")
                imageView.setImageBitmap(image)
            } else {
                Log.d(TAG, "need loading $url")
                noCachedImageUrls.add(url)
            }
        }

        if (noCachedImageUrls.isNotEmpty() && cancelPotentialWork(noCachedImageUrls, imageView)) {
            val task = MultiImageLoaderTask(
                    imageView = imageView,
                    httpConnection = httpConnection,
                    imageCache = imageCache,
                    urls = noCachedImageUrls,
                    resultCallback = this
            )
            val imageViewUniqueId = imageView.hashCode().toString()
            Log.d(TAG, "submit task $imageViewUniqueId")
            asyncHandler.submit(imageViewUniqueId, task)
        }

    }

    private fun cancelPotentialWork(urls: List<String>, imageView: ImageView): Boolean {
        Log.d(TAG, "try to cancelPotentialWork in ${imageView.hashCode()}")
        val imageViewUniqueId = imageView.hashCode().toString()
        val task = asyncHandler.getTask(imageViewUniqueId) as? MultiImageLoaderTask
        if (task != null) {
            val handlingUrls = task.urls
            if (urls == handlingUrls) {
                // уже выполняется загрузка
                Log.d(TAG, "$urls contentDeepEquals $handlingUrls in ${imageView.hashCode()}")
                return false
            } else {
                // если пришел новый url, останавливаем загрузку картинки по старому url
                Log.d(TAG, "stopTask $imageViewUniqueId in ${imageView.hashCode()}")
                asyncHandler.stopTask(imageViewUniqueId)
            }
        }
        Log.d(TAG, "cancelPotentialWork in ${imageView.hashCode()}")
        return true
    }


    override fun onLoaded(taskId: String) {
        asyncHandler.removeTask(taskId)
    }

}

private const val TAG = "MultiImageInteractor"