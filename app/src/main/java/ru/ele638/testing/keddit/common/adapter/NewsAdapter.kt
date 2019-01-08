package ru.ele638.testing.keddit.common.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import ru.ele638.testing.keddit.common.RedditNewsItem

class NewsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val loadingItem = object : ViewType {
        override fun getViewType() = AdapterConstants.LOADING
    }

    init {
        delegateAdapters.put(AdapterConstants.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(AdapterConstants.NEWS, NewsDelegateAdapter())
        items = ArrayList()
        items.add(loadingItem)
        notifyItemInserted(items.lastIndex)
    }


    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType)!!.onCreateView(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position))!!.onBindViewHolder(holder, items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return items.get(position).getViewType()
    }

    fun getNews() : List<RedditNewsItem>{
        return items
            .filter { it.getViewType() == AdapterConstants.NEWS }
            .map { it as RedditNewsItem }
    }


    fun addNews(news: List<RedditNewsItem>){
        val lastIndex = items.lastIndex
        items.removeAt(lastIndex)
        notifyItemRemoved(lastIndex)

        items.addAll(lastIndex, news)
        items.add(loadingItem)
        notifyItemRangeInserted(lastIndex, items.size)

    }

    fun clearAndAddNews(news: List<RedditNewsItem>) {
        items.clear()
        items.addAll(news)
        items.add(loadingItem)
        notifyDataSetChanged()
    }


}