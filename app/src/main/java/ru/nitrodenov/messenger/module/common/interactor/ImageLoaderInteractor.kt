package ru.nitrodenov.messenger.module.common.interactor

import android.widget.ImageView
import ru.nitrodenov.messenger.ImageCache
import ru.nitrodenov.messenger.async.AsyncHandler
import ru.nitrodenov.messenger.async.TaskResultCallback
import ru.nitrodenov.messenger.http.HttpConnection
import ru.nitrodenov.messenger.module.common.interactor.task.ImageLoaderTask

interface ImageLoaderInteractor {

    fun loadImage(imageView: ImageView, url: String?)

}

class ImageLoaderInteractorImpl(private val asyncHandler: AsyncHandler,
                                private val httpConnection: HttpConnection,
                                private val imageCache: ImageCache) : ImageLoaderInteractor, TaskResultCallback {

    override fun loadImage(imageView: ImageView, url: String?) {
        if (url == null || url.isEmpty()) {
            return
        }

        val image = imageCache.getBitmapFromMemoryCache(url)
        if (image != null) {
            imageView.setImageBitmap(image)
        } else if (cancelPotentialWork(url, imageView)) {
            val task = ImageLoaderTask(
                    imageView = imageView,
                    httpConnection = httpConnection,
                    imageCache = imageCache,
                    url = url,
                    resultCallback = this
            )
            val imageViewUniqueId = imageView.hashCode().toString()
            asyncHandler.submit(imageViewUniqueId, task)
        }
    }

    private fun cancelPotentialWork(url: String, imageView: ImageView): Boolean {
        val imageViewUniqueId = imageView.hashCode().toString()
        val task = asyncHandler.getTask(imageViewUniqueId) as? ImageLoaderTask
        if (task != null) {
            val handlingTaskUrl = task.url
            if (handlingTaskUrl != url) {
                // если пришел новый url, останавливаем загрузку картинки по старому url
                asyncHandler.stopTask(imageViewUniqueId)
            } else {
                // уже выполняется загрузка
                return false
            }
        }
        return true
    }

    override fun onLoaded(taskId: String) {
        asyncHandler.removeTask(taskId)
    }

}