package ru.nitrodenov.messenger.module.channels

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nitrodenov.messenger.MessengerApplication
import ru.nitrodenov.messenger.R
import ru.nitrodenov.messenger.di.module.ChannelsModule
import ru.nitrodenov.messenger.module.channels.presenter.ChannelsPresenter
import ru.nitrodenov.messenger.module.channels.router.ChannelsRouter
import ru.nitrodenov.messenger.module.channels.view.ChannelsViewImpl
import javax.inject.Inject

class ChannelsFragment : Fragment() {

    @Inject
    lateinit var presenter: ChannelsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpFragmentComponent(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.channels, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val channelsView = ChannelsViewImpl(view, presenter, presenter)
        presenter.attachView(channelsView)
    }

    override fun onStart() {
        super.onStart()
        presenter.attachRouter(activity as ChannelsRouter)
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
        MessengerApplication.getInstance()
                .component
                .plus(ChannelsModule(state))
                .inject(this)
    }
}

private const val KEY_STATE = "key_state"