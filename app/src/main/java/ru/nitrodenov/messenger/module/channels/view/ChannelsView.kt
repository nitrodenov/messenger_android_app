package ru.nitrodenov.messenger.module.channels.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import ru.nitrodenov.messenger.R
import ru.nitrodenov.messenger.module.channels.ChannelsItemClickListener
import ru.nitrodenov.messenger.module.common.ImagesCallback
import ru.nitrodenov.messenger.module.channels.entity.ChannelsData
import ru.nitrodenov.messenger.module.channels.view.adapter.ChannelsAdapter

interface ChannelsView {

    fun showChannelsData(channelsData: ChannelsData)

    fun showScreenProgress()

    fun showScreenError()

}

class ChannelsViewImpl(view: View,
                       logosCallback: ImagesCallback,
                       itemClickListener: ChannelsItemClickListener) : ChannelsView {

    private val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
    private val adapter: ChannelsAdapter = ChannelsAdapter(logosCallback, itemClickListener)
    private val progress = view.findViewById<View>(R.id.progress)

    init {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Список бесед"
    }

    override fun showChannelsData(channelsData: ChannelsData) {
        adapter.channelsData = channelsData
        adapter.notifyDataSetChanged()
        progress.visibility = View.GONE
    }

    override fun showScreenProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun showScreenError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

private const val TAG = "ChannelsView"