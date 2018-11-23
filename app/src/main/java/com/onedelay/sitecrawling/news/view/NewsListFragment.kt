package com.onedelay.sitecrawling.news.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.onedelay.sitecrawling.R
import com.onedelay.sitecrawling.news.NewsListAdapter
import com.onedelay.sitecrawling.news.utils.DividerItemDecoration
import com.onedelay.sitecrawling.news.model.NewsItem
import com.onedelay.sitecrawling.news.contract.NewsListContract
import com.onedelay.sitecrawling.news.presenter.NewsListPresenter
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.view.*

class NewsListFragment : Fragment(), NewsListAdapter.OnNewsClickListener, NewsListContract.View {
    private val adapter = NewsListAdapter(this)

    private lateinit var rootView: View

    private lateinit var newsListPresenter: NewsListPresenter

    companion object {
        private const val PORTAL = "portal"
        private const val CATEGORY = "category"

        fun newInstance(sectionCategory: String, portal: String): NewsListFragment {
            val fragment = NewsListFragment()
            val args = Bundle()
            args.putString(CATEGORY, sectionCategory)
            args.putString(PORTAL, portal)
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

        newsListPresenter = NewsListPresenter(this, arguments!!.getString(PORTAL), arguments!!.getString(CATEGORY))

        // 새로고침 시 크롤링 시작
        rootView.swipeRefreshLayout.setOnRefreshListener {
            newsListPresenter.requestServer()
        }

        newsListPresenter.requestServer()

        return rootView
    }

    override fun receiveNewsList(items: List<NewsItem>?) {
        adapter.setItems(items ?: listOf())
    }

    override fun hideProgress() {
        progress_bar?.visibility = View.GONE
        rootView.swipeRefreshLayout.isRefreshing = false
    }

    override fun showError() {
        Toast.makeText(context, "서버 통신 오류", Toast.LENGTH_SHORT).show()
    }

    // 이 친구는 presenter 클래스 selectNewsItem 메서드에서 호출됨
    override fun openNewBrowser(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    // selectNewsItem 메서드는 리사이클러뷰에서 뉴스 아이템 클릭시 호출됨
    override fun onNewsClick(data: NewsItem?) {
        if (data != null) {
            newsListPresenter.clickNewsItem(data)
        } else {
            Toast.makeText(context, "데이터가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
