package ru.nitrodenov.messenger.module.channel

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nitrodenov.messenger.MessengerApplication
import ru.nitrodenov.messenger.R
import ru.nitrodenov.messenger.di.module.ChannelModule
import ru.nitrodenov.messenger.module.channel.entity.ChannelToolbarData
import ru.nitrodenov.messenger.module.channel.presenter.ChannelPresenter
import ru.nitrodenov.messenger.module.channel.router.ChannelRouter
import ru.nitrodenov.messenger.module.channel.view.ChannelViewImpl
import javax.inject.Inject

class ChannelFragment : Fragment() {

    @Inject
    lateinit var presenter: ChannelPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpFragmentComponent(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.channel, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val channelView = ChannelViewImpl(
                view = view,
                loadDataCallback = presenter,
                logoCallback = presenter,
                onBackPressedListener = presenter
        )
        presenter.attachView(channelView)
    }

    override fun onStart() {
        super.onStart()
        presenter.attachRouter(activity as ChannelRouter)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_STATE, presenter.getState())
    }

    override fun onStop() {
        super.onStop()
        presenter.detachRouter()
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    private fun setUpFragmentComponent(savedInstanceState: Bundle?) {
        val state: Bundle? = savedInstanceState?.getParcelable(KEY_STATE)
        val id: String = activity.intent.getStringExtra(KEY_ID)
        val channelData: ChannelToolbarData = activity.intent.getParcelableExtra(KEY_CHANNEL_TOOLBAR_DATA)
        MessengerApplication.getInstance()
                .component
                .plus(ChannelModule(state, id, channelData))
                .inject(this)
    }
}

private const val KEY_STATE = "key_state"
private const val KEY_ID = "key_id"
private const val KEY_CHANNEL_TOOLBAR_DATA = "key_channel_toolbar_data"