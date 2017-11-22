package ru.nitrodenov.messenger.module.channel.presenter

import android.os.Bundle
import android.widget.ImageView
import ru.nitrodenov.messenger.module.channel.ChannelDataCallback
import ru.nitrodenov.messenger.module.channel.entity.ChannelData
import ru.nitrodenov.messenger.module.channel.entity.ChannelToolbarData
import ru.nitrodenov.messenger.module.channel.interactor.ChannelDataInteractor
import ru.nitrodenov.messenger.module.channel.router.ChannelRouter
import ru.nitrodenov.messenger.module.channel.view.ChannelView
import ru.nitrodenov.messenger.module.common.ImageCallback
import ru.nitrodenov.messenger.module.common.OnBackPressedListener
import ru.nitrodenov.messenger.module.common.interactor.ImageLoaderInteractor
import ru.nitrodenov.messenger.module.common.interactor.MultiImageLoaderInteractor

interface ChannelPresenter : ChannelDataCallback, ImageCallback, OnBackPressedListener {

    fun attachView(view: ChannelView)

    fun detachView()

    fun attachRouter(router: ChannelRouter)

    fun detachRouter()

    fun getState(): Bundle

    fun loadMoreData(lastItemPosition: Int)

}

class ChannelPresenterImpl(private val channelDataInteractor: ChannelDataInteractor,
                           private val imageLoaderInteractor: ImageLoaderInteractor,
                           private val multiImageLoaderInteractor: MultiImageLoaderInteractor,
                           private val id: String,
                           private val channelToolbarData: ChannelToolbarData,
                           state: Bundle?) : ChannelPresenter {

    private var view: ChannelView? = null
    private var router: ChannelRouter? = null
    private var data: ChannelData? = state?.getParcelable(KEY_STATE)

    override fun attachView(view: ChannelView) {
        this.view = view

        showChannelData()
    }

    override fun detachView() {
        this.view = null
        channelDataInteractor.stopTasks()
    }

    override fun attachRouter(router: ChannelRouter) {
        this.router = router
    }

    override fun detachRouter() {
        this.router = null
    }

    override fun getState(): Bundle {
        val bundle = Bundle()
        bundle.putParcelable(KEY_STATE, data)
        return bundle
    }

    override fun loadMoreData(lastItemPosition: Int) {
        channelDataInteractor.loadChannelData(id, lastItemPosition, this)
    }

    override fun onChannelDataLoaded(channelData: ChannelData) {
        if (this.data == null) {
            this.data = channelData
        } else {
            this.data?.messages?.addAll(channelData.messages)
        }
        bindChannelData(channelData)
    }

    override fun loadLogo(imageView: ImageView, imageUrl: String) {
        imageLoaderInteractor.loadImage(imageView, imageUrl)
    }

    override fun onBackPressed() {
        router?.goBack()
    }

    private fun showChannelData() {
        showChannelToolbarData()
        val data = this.data
        if (data == null) {
            loadMoreData(0)
        } else {
            bindChannelData(data)
        }
    }

    private fun showChannelToolbarData() {
        multiImageLoaderInteractor.loadImages(view?.multiImageView as ImageView, channelToolbarData.imageUrls)
        view?.showToolbarTitle(channelToolbarData.title)
        view?.showToolbarDescription(channelToolbarData.description)
    }

    private fun bindChannelData(data: ChannelData?) {
        if (data != null) {
            view?.showChannelData(data)
        }
    }
}

private const val KEY_STATE = "key_state"