package com.onedelay.sitecrawling.news.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.onedelay.sitecrawling.R
import com.onedelay.sitecrawling.news.contract.ServerContract
import com.onedelay.sitecrawling.news.model.IssueItem
import com.onedelay.sitecrawling.news.presenter.IssueListPresenter
import com.onedelay.sitecrawling.news.utils.DividerItemDecoration
import com.onedelay.sitecrawling.news.view.adapters.IssueListAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), BaseOnClickListener, ServerContract.IssueView {
    private val daumAdapter = IssueListAdapter(this)
    private val naverAdapter = IssueListAdapter(this)

    private val presenter = IssueListPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initList()

        logo.setOnClickListener {
            startActivity(Intent(this, NewsActivity::class.java))
        }

        //putDummy()
        // TODO : ProgressBar 어떻게 띄울지 고민해보기
        presenter.requestNaverIssue()
        presenter.requestDaumIssue()
    }

    private fun putDummy() {
        daumAdapter.setItems(listOf(
                IssueItem(1, "홍태준", "https://www.naver.com"),
                IssueItem(2, "홍태준", "https://www.naver.com"),
                IssueItem(3, "홍태준", "https://www.naver.com"),
                IssueItem(4, "홍태준", "https://www.naver.com"),
                IssueItem(5, "홍태준", "https://www.naver.com"),
                IssueItem(6, "홍태준", "https://www.naver.com")
        ))
        naverAdapter.setItems(listOf(
                IssueItem(1, "원지연", "https://www.naver.com"),
                IssueItem(1, "홍태준", "https://www.naver.com"),
                IssueItem(1, "홍태준", "https://www.naver.com"),
                IssueItem(1, "홍태준", "https://www.naver.com")
        ))
    }

    private fun initList() {
        list_daum_issue.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(baseContext)
            adapter = daumAdapter
            addItemDecoration(DividerItemDecoration(baseContext))
        }

        list_naver_issue.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(baseContext)
            adapter = naverAdapter
            addItemDecoration(DividerItemDecoration(baseContext))
        }
    }

    override fun receiveNaverIssue(items: List<IssueItem>) {
        naverAdapter.setItems(items)
    }

    override fun receiveDaumIssue(items: List<IssueItem>) {
        daumAdapter.setItems(items)
    }

    override fun openNewBrowser(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun showError() {
        Toast.makeText(baseContext, "서버 통신 오류", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(data: Any?) {
        if (data is IssueItem) startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data.url)))
    }

    override fun onLongClick(data: Any?) {
        if (data is IssueItem) {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, data.name)
            sharingIntent.putExtra(Intent.EXTRA_TEXT, data.url)
            startActivity(Intent.createChooser(sharingIntent, resources.getString(R.string.str_share)))
        }
    }
}
