package ru.ele638.testing.keddit.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import ru.ele638.testing.keddit.R
import ru.ele638.testing.keddit.common.extensions.inflate

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateView(parent: ViewGroup): RecyclerView.ViewHolder = TurnsViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType){
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.news_item_loading)) {
    }
}