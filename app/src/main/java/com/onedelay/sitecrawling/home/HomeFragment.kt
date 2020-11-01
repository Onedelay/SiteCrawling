package com.onedelay.sitecrawling.home

import android.content.Intent
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
import com.onedelay.sitecrawling.data.model.entity.IssueItem
import com.onedelay.sitecrawling.base.BaseOnClickListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), BaseOnClickListener, IssueContract.View {

    private val presenter = IssueListPresenter(this)

    private val issueListAdapter = IssueListAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()

        presenter.requestNaverIssue()
    }

    override fun onDestroy() {
        presenter.onDestroy()

        super.onDestroy()
    }

    override fun onClick(data: Any?) {
        if (data is IssueItem) presenter.clickItem(data)
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

    override fun receiveNaverIssue(items: List<IssueItem>) {
        issueListAdapter.setItems(items)
    }

    override fun openNewBrowser(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(context, "error#$errorMessage", Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        fl_progress_area?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        fl_progress_area?.visibility = View.GONE
    }

    private fun initializeViews() {
        with(rv_naver_issue) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = issueListAdapter
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }

}