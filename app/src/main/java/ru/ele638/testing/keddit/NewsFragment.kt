package ru.ele638.testing.keddit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_news.*
import ru.ele638.testing.keddit.common.RedditNews
import ru.ele638.testing.keddit.common.adapter.InfiniteScrollListner
import ru.ele638.testing.keddit.common.adapter.NewsAdapter
import ru.ele638.testing.keddit.common.adapter.NewsManager
import ru.ele638.testing.keddit.common.extensions.inflate
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class NewsFragment : Fragment() {

    var redditNews: RedditNews? = null
    var compositeSubsctions = CompositeSubscription()
    val newsManager by lazy { NewsManager() }

    companion object {
        private val REDDIT_KEY = "redditNews"
    }

    override fun onResume() {
        super.onResume()
        compositeSubsctions = CompositeSubscription()
    }

    override fun onPause() {
        super.onPause()
        compositeSubsctions.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return container?.inflate(R.layout.fragment_news)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        news_list.apply {
            val llm = LinearLayoutManager(context)
            setHasFixedSize(true)
            layoutManager = llm
            clearOnScrollListeners()
            addOnScrollListener(
                InfiniteScrollListner(
                    { requestNews() }, llm
                )
            )
        }

        initAdapter()

        if (savedInstanceState != null && savedInstanceState.containsKey(REDDIT_KEY)) {
            redditNews = savedInstanceState.get(REDDIT_KEY) as RedditNews
            (news_list.adapter as NewsAdapter).clearAndAddNews(redditNews!!.news)
        }else{
            requestNews()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val news = (news_list.adapter as NewsAdapter).getNews()
        if (redditNews != null && news.size > 0){
            outState.putParcelable(REDDIT_KEY, redditNews?.copy(news = news))
        }
    }

    private fun initAdapter() {
        if (news_list.adapter == null) {
            news_list.adapter = NewsAdapter()
        }
    }

    private fun requestNews() {
        val subscriber = newsManager.getNews(redditNews?.after ?: "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { retrievedNews ->
                    redditNews = retrievedNews
                    (news_list.adapter as NewsAdapter).addNews(retrievedNews.news)
                    Log.d("Subscriber", Thread.currentThread().name)
                }, { e ->
                    Log.d("SubscriberError", e.toString())
                })
        compositeSubsctions.add(subscriber)
    }

    fun scrollTop(){
        news_list.smoothScrollToPosition(0)
    }
}
