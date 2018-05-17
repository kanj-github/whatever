package com.example.kanj.whatever

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

class PullsAdapter(val list: ArrayList<PullRequest>)
    : RecyclerView.Adapter<PullsAdapter.ViewHolder>() {

    //private val mOnClickListener: View.OnClickListener

    /*init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }*/

    fun appendItemsToList(items: ArrayList<PullRequest>) {
        val insertPosition = list.size
        list.addAll(items)
        notifyItemRangeInserted(insertPosition, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pull, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.title.text = item.title
        holder.user.text = item.user.login
        if (TextUtils.isEmpty(item.body)) {
            holder.body.visibility = View.GONE
        } else {
            holder.body.text = item.body
        }

        Picasso.get().load(item.user.avatar_url).into(holder.icon)

        /*with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }*/
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val title: TextView = mView.title
        val user: TextView = mView.user
        val body: TextView = mView.body
        val icon: ImageView = mView.icon
    }
}
