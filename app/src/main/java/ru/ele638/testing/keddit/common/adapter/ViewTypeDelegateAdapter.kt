package ru.ele638.testing.keddit.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup


interface ViewTypeDelegateAdapter {
    fun onCreateView(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}