package ru.ele638.testing.keddit.common.Network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RestAPI {
    private val redditAPI : RedditAPI

    init {
        redditAPI = Retrofit.Builder()
            .baseUrl("https://www.reddit.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(RedditAPI::class.java)
    }

    fun getNews(after: String, limit: String) : Call<RedditNewsResponse> = redditAPI.getTop(after, limit)
}