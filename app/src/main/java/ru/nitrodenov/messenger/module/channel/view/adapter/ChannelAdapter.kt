package ru.nitrodenov.messenger.module.channel.view.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import ru.nitrodenov.messenger.R
import ru.nitrodenov.messenger.module.channel.entity.Message
import ru.nitrodenov.messenger.module.channel.presenter.ChannelPresenter
import ru.nitrodenov.messenger.module.common.ImageCallback

class ChannelAdapter(private val loadDataCallback: ChannelPresenter,
                     private val logoCallback: ImageCallback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val messages: ArrayList<Message> = ArrayList()
    private var isLoading = false

    override fun getItemCount(): Int = messages.size + 1 //plus loading cell

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder =
            when (viewType) {
                MY_MESSAGE_VIEW_TYPE -> {
                    val view = LayoutInflater.from(parent?.context).inflate(R.layout.my_message_item, parent, false)
                    MyMessageViewHolder(view)
                }
                INCOME_MESSAGE_VIEW_TYPE -> {
                    val view = LayoutInflater.from(parent?.context).inflate(R.layout.income_message_item, parent, false)
                    IncomeMessageViewHolder(view)
                }
                LOADING_VIEW_TYPE -> {
                    val view = LayoutInflater.from(parent?.context).inflate(R.layout.loading_item, parent, false)
                    LoadingViewHolder(view)
                }
                else -> {
                    val view = LayoutInflater.from(parent?.context).inflate(R.layout.my_message_item, parent, false)
                    MyMessageViewHolder(view)
                }
            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder?.itemViewType) {
            MY_MESSAGE_VIEW_TYPE -> {
                val viewHolder = holder as MyMessageViewHolder
                viewHolder.messageTextView.text = messages[position].text
                viewHolder.timeTextView.text = messages[position].time
                showImage(viewHolder.messageImageView, messages[position].imageInMessage)
            }
            INCOME_MESSAGE_VIEW_TYPE -> {
                val viewHolder = holder as IncomeMessageViewHolder
                viewHolder.messageTextView.text = messages[position].text
                viewHolder.timeTextView.text = messages[position].time
                showImage(viewHolder.chatImageView, messages[position].logo)
                showImage(viewHolder.messageImageView, messages[position].imageInMessage)
            }
        }

        if (shouldLoadMoreData(position)) {
            loadMoreData()
        }
    }

    override fun getItemViewType(position: Int): Int = when {
        position == messages.size -> LOADING_VIEW_TYPE
        messages[position].isIncomeMessage -> INCOME_MESSAGE_VIEW_TYPE
        else -> MY_MESSAGE_VIEW_TYPE
    }

    fun addMessages(newMessages: List<Message>) {
        isLoading = false
        if (messages.isEmpty()) {
            messages.addAll(newMessages)
            notifyDataSetChanged()
        } else {
            val lastPosition = messages.size
            messages.addAll(newMessages)
            notifyItemRangeInserted(lastPosition, newMessages.size)
        }

    }

    private fun showImage(imageView: ImageView, imageUrl: String?) {
        if (imageUrl != null) {
            addPlaceHolder(imageView)
            logoCallback.loadLogo(imageView, imageUrl)
        } else {
            imageView.setImageBitmap(null)
            imageView.visibility = View.GONE
        }
    }

    private fun addPlaceHolder(imageView: ImageView) {
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.color.gray))
        imageView.visibility = View.VISIBLE
    }

    private fun shouldLoadMoreData(position: Int): Boolean =
            messages.size - position - 1 in 0..(OFFSET - 1) && !isLoading

    private fun loadMoreData() {
        isLoading = true
        loadDataCallback.loadMoreData(messages.size)
    }
}

private const val MY_MESSAGE_VIEW_TYPE = 0
private const val INCOME_MESSAGE_VIEW_TYPE = 1
private const val LOADING_VIEW_TYPE = 2

private const val OFFSET = 5