package ru.nitrodenov.messenger.module.common

import android.widget.ImageView

interface ImageCallback {

    fun loadLogo(imageView: ImageView, imageUrl: String)

}