package com.onedelay.sitecrawling.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onedelay.sitecrawling.Constants
import com.onedelay.sitecrawling.R
import com.onedelay.sitecrawling.news.crawler.DaumNewsAsyncTask
import com.onedelay.sitecrawling.news.crawler.NaverNewsAsyncTask
import com.onedelay.sitecrawling.news.crawler.NewsAsyncTask
import kotlinx.android.synthetic.main.fragment_news.view.*

class PlaceholderFragment : Fragment(), NewsListAdapter.OnNewsClickListener, NewsAsyncTask.OnTaskComplete {
    private val adapter = NewsListAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_news, container, false)

        rootView.recyclerView.setHasFixedSize(true)
        rootView.recyclerView.layoutManager = LinearLayoutManager(context)
        rootView.recyclerView.adapter = adapter

        val target = activity?.getPreferences(Context.MODE_PRIVATE)?.getString(Constants.SELECTED_PORTAL, Constants.NAVER)
        val category = arguments!!.getString(CATEGORY)
        val task =
                if (target == Constants.DAUM) DaumNewsAsyncTask(category, this)
                else NaverNewsAsyncTask(category, this)
        task.execute()

        return rootView
    }

    companion object {
        private const val CATEGORY = "category"

        fun newInstance(sectionCategory: String): PlaceholderFragment {
            val fragment = PlaceholderFragment()
            val args = Bundle()
            args.putString(CATEGORY, sectionCategory)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onNewsClick(data: NewsItem?) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data?.url)))
    }

    override fun onTaskComplete(list: List<NewsItem>?) {
        adapter.setItems(list!!)
    }
}