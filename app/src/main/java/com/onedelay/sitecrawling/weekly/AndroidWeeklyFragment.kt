package com.onedelay.sitecrawling.weekly

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
import com.onedelay.sitecrawling.data.model.entity.WeeklyItem
import com.onedelay.sitecrawling.data.source.RetrofitService
import kotlinx.android.synthetic.main.fragment_android_weekly.*

class AndroidWeeklyFragment : Fragment(), AndroidWeeklyContract.View {

    private val weeklyAdapter = WeeklyListAdapter()

    private val presenter = AndroidWeeklyPresenter(this, RetrofitService.create())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_android_weekly, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(rv_weekly_list) {
            adapter = weeklyAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }

        presenter.requestAndroidWeekly()
    }

    override fun onDestroy() {
        presenter.onDestroy()

        super.onDestroy()
    }

    override fun receiveList(items: List<WeeklyItem>) {
        weeklyAdapter.submitList(items)
    }

    override fun openNewBrowser(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun showProgress() {
        fl_progress_area.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        fl_progress_area.visibility = View.GONE
    }

    override fun showError(message: String) {
        Toast.makeText(context, "error#$message", Toast.LENGTH_SHORT).show()
    }

}
