package com.example.kanj.whatever

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.kanj.api.response.PullRequest
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pull.view.*

class PagedPullsAdapter : PagedListAdapter<PullRequest, PagedPullsAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : PagedPullsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pull, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.title.text = item.title
            holder.user.text = item.user.login
            if (TextUtils.isEmpty(item.body)) {
                holder.body.visibility = View.GONE
            } else {
                holder.body.visibility = View.VISIBLE
                holder.body.text = item.body
            }

            holder.icon.visibility = View.VISIBLE
            Picasso.get().load(item.user.avatar_url).into(holder.icon)
        } else {
            // What to do man?
            holder.title.text = ""
            holder.user.text = ""
            holder.body.visibility = View.GONE
            holder.icon.visibility = View.INVISIBLE
        }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val title: TextView = mView.title
        val user: TextView = mView.user
        val body: TextView = mView.body
        val icon: ImageView = mView.icon
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PullRequest>() {
            override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest) =
                    oldItem.html_url == newItem.html_url

            override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest) =
                    oldItem == newItem
        }
    }
}