package ru.nitrodenov.messenger.module.common

import ru.nitrodenov.messenger.widgets.MultipleImageView

interface ImagesCallback {

    fun loadLogos(imageView: MultipleImageView, imageUrls: List<String>)

}