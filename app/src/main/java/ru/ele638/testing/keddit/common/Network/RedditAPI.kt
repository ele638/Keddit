package ru.ele638.testing.keddit.common.Network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditAPI {
    @GET("/top.json")
    fun getTop(
        @Query("after") after: String,
        @Query("before") before: String
    ) : Call<RedditNewsResponse>
}