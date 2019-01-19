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
import com.onedelay.sitecrawling.news.contract.ServerContract
import com.onedelay.sitecrawling.news.model.NewsItem
import com.onedelay.sitecrawling.news.presenter.NewsListPresenter
import com.onedelay.sitecrawling.news.utils.DividerItemDecoration
import com.onedelay.sitecrawling.news.view.adapters.NewsListAdapter
import kotlinx.android.synthetic.main.fragment_news.*

class NewsListFragment : Fragment(), BaseOnClickListener, ServerContract.View {
    private val adapter = NewsListAdapter(this)

    private lateinit var newsListPresenter: NewsListPresenter

    companion object {
        private const val ARGUMENT_PORTAL = "portal"
        private const val ARGUMENT_CATEGORY = "category"

        fun newInstance(sectionCategory: String, portal: String): NewsListFragment {
            val fragment = NewsListFragment()
            val args = Bundle()
            args.putString(ARGUMENT_CATEGORY, sectionCategory)
            args.putString(ARGUMENT_PORTAL, portal)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context!!))
        registerForContextMenu(recyclerView)

        val portal = arguments?.getString(ARGUMENT_PORTAL) ?: ""
        val category = arguments?.getString(ARGUMENT_CATEGORY) ?: ""
        newsListPresenter = NewsListPresenter(this, portal, category)

        // 새로고침 시 크롤링 시작
        swipeRefreshLayout.setOnRefreshListener {
            newsListPresenter.requestServer()
        }

        newsListPresenter.requestServer()
    }

    override fun receiveList(items: List<NewsItem>?) {
        adapter.setItems(items ?: listOf())
    }

    override fun hideProgress() {
        progress_bar?.visibility = View.GONE
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun showError() {
        Toast.makeText(context, "서버 통신 오류", Toast.LENGTH_SHORT).show()
    }

    // 이 친구는 presenter 클래스 selectNewsItem 메서드에서 호출됨
    override fun openNewBrowser(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    // selectNewsItem 메서드는 리사이클러뷰에서 뉴스 아이템 클릭시 호출됨
    override fun onClick(data: Any?) {
        if (data != null) {
            newsListPresenter.clickNewsItem(data as NewsItem)
        } else {
            Toast.makeText(context, "데이터가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // Share plain text using intent (to all messaging apps)
    // https://stackoverflow.com/questions/9948373/android-share-plain-text-using-intent-to-all-messaging-apps
    override fun onLongClick(data: Any?) {
        if (data is NewsItem) {
            val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, data.name)
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, data.url)
            startActivity(Intent.createChooser(sharingIntent, resources.getString(R.string.str_share)))
        }
    }
}
