package ru.nitrodenov.messenger.module.channels.presenter

import android.os.Bundle
import android.view.View
import ru.nitrodenov.messenger.module.channel.entity.ChannelToolbarData
import ru.nitrodenov.messenger.module.channels.ChannelsDataCallback
import ru.nitrodenov.messenger.module.channels.ChannelsItemClickListener
import ru.nitrodenov.messenger.module.channels.entity.ChannelsData
import ru.nitrodenov.messenger.module.channels.interactor.ChannelsDataInteractor
import ru.nitrodenov.messenger.module.channels.router.ChannelsRouter
import ru.nitrodenov.messenger.module.channels.view.ChannelsView
import ru.nitrodenov.messenger.module.common.ImagesCallback
import ru.nitrodenov.messenger.module.common.interactor.MultiImageLoaderInteractor
import ru.nitrodenov.messenger.widgets.MultipleImageView

interface ChannelsPresenter : ChannelsDataCallback, ImagesCallback, ChannelsItemClickListener {

    fun attachView(view: ChannelsView)

    fun detachView()

    fun attachRouter(router: ChannelsRouter)

    fun detachRouter()

    fun getState(): Bundle

}

class ChannelsPresenterImpl(private val channelsDataInteractor: ChannelsDataInteractor,
                            private val multiImageLoaderInteractor: MultiImageLoaderInteractor,
                            state: Bundle?) : ChannelsPresenter {

    private var view: ChannelsView? = null
    private var router: ChannelsRouter? = null
    private var data: ChannelsData? = state?.getParcelable(KEY_STATE)

    override fun attachView(view: ChannelsView) {
        this.view = view

        showChannelsData()
    }

    override fun detachView() {
        this.view = null
        stopTask()
    }

    override fun attachRouter(router: ChannelsRouter) {
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

    override fun onChannelsDataLoaded(channelsData: ChannelsData) {
        this.data = channelsData
        bindChannelsData(data)
    }

    override fun loadLogos(imageView: MultipleImageView, imageUrls: List<String>) {
        multiImageLoaderInteractor.loadImages(imageView, imageUrls)
    }

    override fun onItemClick(id: String, channelToolbarData: ChannelToolbarData, titleTextView: View, descriptionTextView: View) {
        router?.goToChannel(id, channelToolbarData, titleTextView, descriptionTextView)
    }

    private fun showChannelsData() {
        val data = this.data
        if (data == null) {
            loadChannelsData()
        } else {
            bindChannelsData(data)
        }
    }

    private fun loadChannelsData() {
        channelsDataInteractor.loadChannelsData("channels_data", this)
    }

    private fun bindChannelsData(data: ChannelsData?) {
        if (data != null) {
            view?.showChannelsData(data)
        }
    }

    private fun stopTask() {
        channelsDataInteractor.stopTask("channels_data")
    }
}

private const val KEY_STATE = "key_state"