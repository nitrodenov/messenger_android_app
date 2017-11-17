package ru.nitrodenov.messenger

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

interface ImageCache {

    fun getBitmapFromDiskCache(id: String): Bitmap?

    fun addBitmapToDiskCache(id: String, bitmap: Bitmap)

    fun getBitmapFromMemoryCache(id: String): Bitmap?

    fun addBitmapToMemoryCache(id: String, bitmap: Bitmap)

}

class ImageCacheImpl(private val inMemoryCache: LruCache<String, Bitmap>,
                     private val directory: File) : ImageCache {

    override fun getBitmapFromDiskCache(id: String): Bitmap? {
        try {
            val file = File(directory, id)
            return BitmapFactory.decodeStream(FileInputStream(file))
        } catch (e: FileNotFoundException) {

        }
        return null
    }

    override fun addBitmapToDiskCache(id: String, bitmap: Bitmap) {
        synchronized(this) {
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val file = File(directory, id)
            if (file.exists()) {
                return
            }
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            } finally {
                fos?.close()
            }
        }
    }

    override fun getBitmapFromMemoryCache(id: String): Bitmap? = inMemoryCache.get(id)

    override fun addBitmapToMemoryCache(id: String, bitmap: Bitmap) {
        inMemoryCache.put(id, bitmap)
    }

}