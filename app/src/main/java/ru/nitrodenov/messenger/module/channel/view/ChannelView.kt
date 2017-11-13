package ru.nitrodenov.messenger.module.channel.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import ru.nitrodenov.messenger.R
import ru.nitrodenov.messenger.module.common.ImageCallback
import ru.nitrodenov.messenger.module.common.OnBackPressedListener
import ru.nitrodenov.messenger.module.channel.entity.ChannelData
import ru.nitrodenov.messenger.module.channel.presenter.ChannelPresenter
import ru.nitrodenov.messenger.module.channel.view.adapter.ChannelAdapter
import ru.nitrodenov.messenger.widgets.MultipleImageView

interface ChannelView {

    var multiImageView: MultipleImageView

    fun showChannelData(channelData: ChannelData)

    fun showScreenProgress()

    fun showScreenError()

    fun showToolbarTitle(title: String)

    fun showToolbarDescription(description: String)

}

class ChannelViewImpl(view: View,
                      loadDataCallback: ChannelPresenter,
                      logoCallback: ImageCallback,
                      onBackPressedListener: OnBackPressedListener) : ChannelView {

    private val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
    private val adapter: ChannelAdapter = ChannelAdapter(loadDataCallback, logoCallback)
    private val progress = view.findViewById<View>(R.id.progress)

    private val titleTextView: TextView
    private val descriptionTextView: TextView

    override var multiImageView: MultipleImageView = view.findViewById(R.id.logos_multi_image_view)

    init {
        val toolbar = view.findViewById<Toolbar>(R.id.channel_toolbar)
        toolbar.navigationIcon = view.resources.getDrawable(R.drawable.ic_button_back)
        toolbar.setNavigationOnClickListener { onBackPressedListener.onBackPressed() }

        titleTextView = toolbar.findViewById(R.id.title_text_view)
        descriptionTextView = toolbar.findViewById(R.id.description_text_view)

        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
        layoutManager.reverseLayout = true
        recyclerView.layoutManager = layoutManager
    }

    override fun showChannelData(channelData: ChannelData) {
        val messages = channelData.messages
        adapter.addMessages(messages)
        progress.visibility = View.GONE
    }

    override fun showScreenProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun showScreenError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showToolbarTitle(title: String) {
        titleTextView.text = title
    }

    override fun showToolbarDescription(description: String) {
        //fixme for debug
        descriptionTextView.text = "4 участника"
    }
}