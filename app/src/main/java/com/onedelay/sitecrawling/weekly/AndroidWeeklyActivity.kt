package com.onedelay.sitecrawling.weekly

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.onedelay.sitecrawling.R
import com.onedelay.sitecrawling.news.model.RetrofitService
import com.onedelay.sitecrawling.news.model.entity.WeeklyItem
import kotlinx.android.synthetic.main.activity_android_weekly.*

class AndroidWeeklyActivity : AppCompatActivity(), Contract.View {

    private val adapter = WeeklyListAdapter()

    private val presenter = AndroidWeeklyPresenter(this, RetrofitService.create())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_weekly)

        with(rv_weekly_list) {
            adapter = this@AndroidWeeklyActivity.adapter

            layoutManager = LinearLayoutManager(context)

            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }

        presenter.requestServer()
    }

    override fun onDestroy() {
        presenter.onDestroy()

        super.onDestroy()
    }

    override fun receiveList(items: List<WeeklyItem>?) {
        adapter.submitList(items)
    }

    override fun openNewBrowser(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun hideProgress() {
        progress_bar.visibility = View.GONE
    }

    override fun showError() {
        Toast.makeText(baseContext, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
    }

}
