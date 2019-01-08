package ru.ele638.testing.keddit.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.news_item.view.*
import ru.ele638.testing.keddit.R
import ru.ele638.testing.keddit.common.RedditNewsItem
import ru.ele638.testing.keddit.common.extensions.getFriendlyTime
import ru.ele638.testing.keddit.common.extensions.inflate
import ru.ele638.testing.keddit.common.extensions.loadImg

class NewsDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateView(parent: ViewGroup): RecyclerView.ViewHolder = TurnsViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TurnsViewHolder
        holder.bind(item as RedditNewsItem)
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.news_item)
    ) {
        fun bind(item: RedditNewsItem) = with(itemView) {
            news_title.text = item.title
            news_author.text = item.author
            news_date.text = item.created.getFriendlyTime()
            news_image.loadImg(item.thumbnail)
            news_likes.text = "${item.numComments} comments"
        }
    }
}