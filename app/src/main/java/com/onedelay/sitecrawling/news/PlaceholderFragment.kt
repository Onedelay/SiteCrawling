package com.onedelay.sitecrawling.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onedelay.sitecrawling.Constants
import com.onedelay.sitecrawling.R
import com.onedelay.sitecrawling.news.crawler.DaumNewsAsyncTask
import com.onedelay.sitecrawling.news.crawler.NaverNewsAsyncTask
import com.onedelay.sitecrawling.news.crawler.NewsAsyncTask
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceholderFragment : Fragment(), NewsListAdapter.OnNewsClickListener, NewsAsyncTask.OnTaskComplete {
    private val adapter = NewsListAdapter(this)
    private lateinit var rootView: View

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_news, container, false)

        rootView.recyclerView.setHasFixedSize(true)
        rootView.recyclerView.layoutManager = LinearLayoutManager(context)
        rootView.recyclerView.adapter = adapter
        rootView.recyclerView.addItemDecoration(DividerItemDecoration(context!!))

        rootView.progress_bar.visibility = View.VISIBLE

        // 프래그먼트 생성시 최초 크롤링 시작
//        startCrawling()

        // 새로고침 시 크롤링 시작
//        rootView.swipeRefreshLayout.setOnRefreshListener {
//            startCrawling()
//        }

        requestServer()

        return rootView
    }

    private fun requestServer() {
        if (arguments?.getString(CATEGORY)!! == Constants.NAVER) {
            RetrofitService.create().getNaverNews().enqueue(object : Callback<List<NewsItem>> {
                override fun onFailure(call: Call<List<NewsItem>>, t: Throwable) {
                    Log.d("SERVER_TEST", t.message)
                }

                override fun onResponse(call: Call<List<NewsItem>>, response: Response<List<NewsItem>>) {
                    val body = response.body()
                    adapter.setItems(body!!)
                    progress_bar?.visibility = View.GONE
                }
            })
        } else {
            RetrofitService.create().getDaumNews().enqueue(object : Callback<List<NewsItem>> {
                override fun onFailure(call: Call<List<NewsItem>>, t: Throwable) {
                    Log.d("SERVER_TEST", t.message)
                }

                override fun onResponse(call: Call<List<NewsItem>>, response: Response<List<NewsItem>>) {
                    val body = response.body()
                    adapter.setItems(body!!)
                    progress_bar?.visibility = View.GONE
                }
            })
        }
    }

    private fun startCrawling() {
        adapter.removeAll()
        val target = activity?.getPreferences(Context.MODE_PRIVATE)?.getString(Constants.SELECTED_PORTAL, Constants.NAVER)
        val category = arguments!!.getString(CATEGORY)
        val task =
                if (target == Constants.DAUM) DaumNewsAsyncTask(category, this)
                else NaverNewsAsyncTask(category, this)
        task.execute()
    }

    override fun onNewsClick(data: NewsItem?) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data?.url)))
    }

    override fun onTaskComplete(list: List<NewsItem>?) {
        adapter.setItems(list!!)
        rootView.swipeRefreshLayout.isRefreshing = false
        progress_bar?.visibility = View.GONE
    }
}
