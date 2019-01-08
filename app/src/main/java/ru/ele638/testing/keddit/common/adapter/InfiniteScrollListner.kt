package ru.ele638.testing.keddit.common.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

class InfiniteScrollListner(
    val func: () -> Unit,
    val layoutManager: LinearLayoutManager)
    : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var visibleTreshold = 2

    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var firstVisibleItem = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0){
            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

            if (loading){
                if (totalItemCount > previousTotal){
                    loading = false
                    previousTotal = totalItemCount
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleTreshold)) {
                // End reached
                Log.i("InfiniteScroll", "End reached")
                func()
                loading = true
            }
        }
    }
}