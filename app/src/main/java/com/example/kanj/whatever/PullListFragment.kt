package com.example.kanj.whatever

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.kanj.api.response.PullRequest
import com.example.kanj.base.AbstractActivity
import com.example.kanj.base.AbstractFragment
import com.example.kanj.base.PullListPresenter
import com.example.kanj.base.PullListScene
import com.example.kanj.whatever.dagger.ActivityComponent
import kotlinx.android.synthetic.main.fragment_pullrequest.*
import javax.inject.Inject

const val LOAD_MORE_THRESHOLD = 3

class PullListFragment : AbstractFragment<ActivityComponent, PullListScene>(), PullListScene {
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var adapter: PullsAdapter
    lateinit var layoutManager: LinearLayoutManager
    var isLoadingMore: Boolean = false
    var shouldLoadMore: Boolean = true

    @Inject lateinit var presenter: PullListPresenter

    override fun injectFragment(injector: ActivityComponent) {
        injector.inject(this)
    }

    override fun getFragmentPresenter() = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pullrequest, container, false)

        recyclerView = view.findViewById(R.id.list)
        progressBar = view.findViewById(R.id.progress_bar)

        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        adapter = PullsAdapter(ArrayList())
        recyclerView.adapter = adapter
        recyclerView.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visible = layoutManager.childCount
                    val total = layoutManager.itemCount
                    val firstVisible = layoutManager.findLastVisibleItemPosition()

                    if (shouldLoadMore && !isLoadingMore && (visible + firstVisible + LOAD_MORE_THRESHOLD) >= total) {
                        shouldLoadMore = presenter.fetchMore()
                        isLoadingMore = shouldLoadMore
                    }
                }
            }
        })
        return view
    }

    override fun addPullItemsToList(items: ArrayList<PullRequest>) {
        progressBar.visibility = View.GONE
        adapter.appendItemsToList(items)
        isLoadingMore = false
    }

    override fun setToolbarTitle(title: String) {
        (activity as AbstractActivity<*, *, *>).setToolbarTitle(title)
    }

    override fun showNoDataError() {
        progressBar.visibility = View.GONE
        msg_no_data.visibility = View.VISIBLE
    }
}
