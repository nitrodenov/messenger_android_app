package ru.nitrodenov.messenger.module.channel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.nitrodenov.messenger.module.channel.entity.ChannelToolbarData
import ru.nitrodenov.messenger.module.channel.router.ChannelRouter

class ChannelActivity : AppCompatActivity(), ChannelRouter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(android.R.id.content, ChannelFragment())
                    .commit()
        }
    }

    override fun goBack() {
        finish()
    }

}

private const val KEY_ID = "key_id"
private const val KEY_CHANNEL_TOOLBAR_DATA = "key_channel_toolbar_data"

fun createChannelIntent(context: Context, id: String, channelToolbarData: ChannelToolbarData): Intent {
    return Intent(context, ChannelActivity::class.java)
            .putExtra(KEY_ID, id)
            .putExtra(KEY_CHANNEL_TOOLBAR_DATA, channelToolbarData)
}