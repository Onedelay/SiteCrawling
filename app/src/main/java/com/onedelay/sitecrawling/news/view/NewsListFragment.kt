package com.onedelay.sitecrawling.news.view

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onedelay.sitecrawling.R
import com.onedelay.sitecrawling.base.BaseNewsItem
import com.onedelay.sitecrawling.base.BaseOnClickListener
import com.onedelay.sitecrawling.news.NewsContract
import com.onedelay.sitecrawling.news.NewsListPresenter
import kotlinx.android.synthetic.main.fragment_news.*

class NewsListFragment : Fragment(), BaseOnClickListener, NewsContract.View {

    private val newsListAdapter = NewsListAdapter(this)

    private lateinit var presenter: NewsListPresenter

    private lateinit var sharedPref: SharedPreferences

    private lateinit var portal: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()

        presenter = NewsListPresenter(view = this, selectedPortal = NewsListPresenter.Portal.Daum)
        presenter.requestNewsList()
    }

    override fun onDestroy() {
        presenter.onDestroy()

        super.onDestroy()
    }

    override fun receiveList(items: List<BaseNewsItem>) {
        newsListAdapter.setItems(items)
    }

    override fun showProgress() {
        fl_progress_area?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        fl_progress_area?.visibility = View.GONE
    }

    override fun showError(message: String) {
        Toast.makeText(context, "error#$message", Toast.LENGTH_SHORT).show()
    }

    override fun openNewBrowser(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onClick(data: Any?) {
        if (data != null && data is BaseNewsItem) {
            presenter.clickNewsItem(data)
        } else {
            Toast.makeText(context, "데이터가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLongClick(data: Any?) {
        if (data is BaseNewsItem) {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, data.title)
            sharingIntent.putExtra(Intent.EXTRA_TEXT, data.url)
            startActivity(Intent.createChooser(sharingIntent, resources.getString(R.string.str_share)))
        }
    }

    private fun initializeViews() {
        with(rv_news_list) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = newsListAdapter
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            registerForContextMenu(this)
        }
    }

}
