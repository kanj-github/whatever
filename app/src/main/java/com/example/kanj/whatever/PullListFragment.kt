package com.example.kanj.whatever

import android.arch.paging.PagedList
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

class PullListFragment : AbstractFragment<ActivityComponent, PullListScene>(), PullListScene {
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var pagedAdapter : PagedPullsAdapter
    lateinit var layoutManager: LinearLayoutManager

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
        pagedAdapter = PagedPullsAdapter()
        recyclerView.adapter = pagedAdapter
        return view
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun setPagedPullList(pagedList: PagedList<PullRequest>) {
        pagedAdapter.submitList(pagedList)
    }

    override fun setToolbarTitle(title: String) {
        (activity as AbstractActivity<*, *, *>).setToolbarTitle(title)
    }

    override fun showNoDataError() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        msg_no_data.visibility = View.VISIBLE
    }
}
