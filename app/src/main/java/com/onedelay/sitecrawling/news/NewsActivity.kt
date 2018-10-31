package com.onedelay.sitecrawling.news

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.onedelay.sitecrawling.R
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.fragment_news.view.*
import org.jsoup.Jsoup
import java.io.IOException

class NewsActivity : AppCompatActivity() {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    companion object {
        val categories = listOf("정치", "경제", "사회", "생활/문화", "세계", "과학")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        setSupportActionBar(toolbar)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        container.adapter = mSectionsPagerAdapter

        for (category in categories) {
            tabs.addTab(tabs.newTab().setText(category))
        }

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_news, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int) = PlaceholderFragment.newInstance(categories[position])

        override fun getCount() = categories.size
    }

    class PlaceholderFragment : Fragment(), NewsListAdapter.OnNewsClickListener, NaverNewsAsyncTask.OnTaskComplete {
        private val adapter = NewsListAdapter(this)
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_news, container, false)

            rootView.recyclerView.setHasFixedSize(true)
            rootView.recyclerView.layoutManager = LinearLayoutManager(context)
            rootView.recyclerView.adapter = adapter

            val task = NaverNewsAsyncTask(arguments!!.getString(CATEGORY), this)
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

        override fun onTaskComplete(list: List<NewsItem>) {
            adapter.setItems(list)
        }
    }
}

class NaverNewsAsyncTask(private val category: String, private val listener: OnTaskComplete) : AsyncTask<Void, Void, List<NewsItem>>() {

    interface OnTaskComplete {
        fun onTaskComplete(list: List<NewsItem>)
    }

    override fun doInBackground(vararg p0: Void?): List<NewsItem> {
        val data = ArrayList<NewsItem>()
        try {
            val doc = Jsoup.connect("https://news.naver.com/").get()
            val elements = doc.select("ul.section_list_ranking")
            for ((i, element) in elements.withIndex()) {
                if (NewsActivity.categories[i] == category) {
                    for ((j, child) in element.children().withIndex()) {
                        val title = child.select("a").attr("title")
                        val url = "https://news.naver.com/" + child.select("a").attr("href")
                        data.add(NewsItem(NewsActivity.categories[i], j + 1, title, url))
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return data
    }

    override fun onPostExecute(result: List<NewsItem>?) {
        listener.onTaskComplete(result!!)
    }
}
