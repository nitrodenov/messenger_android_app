package ru.nitrodenov.messenger.module.channels

import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.view.View
import ru.nitrodenov.messenger.R
import ru.nitrodenov.messenger.module.channel.createChannelIntent
import ru.nitrodenov.messenger.module.channel.entity.ChannelToolbarData
import ru.nitrodenov.messenger.module.channels.router.ChannelsRouter


class ChannelsActivity : AppCompatActivity(), ChannelsRouter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(android.R.id.content, ChannelsFragment())
                    .commit()
        }
    }

    override fun goToChannel(id: String, channelToolbarData: ChannelToolbarData, titleTextView: View, descriptionTextView: View) {
        val intent = createChannelIntent(this, id, channelToolbarData)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    Pair(titleTextView, getString(R.string.activity_title_text_view_transition)),
                    Pair(descriptionTextView, getString(R.string.activity_description_text_view_transition))
            )
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }
}
