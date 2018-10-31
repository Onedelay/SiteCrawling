package com.onedelay.sitecrawling.news

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.preference.Preference
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.onedelay.sitecrawling.Constants
import com.onedelay.sitecrawling.R
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.fragment_news.view.*
import org.jsoup.Jsoup
import java.io.IOException

class NewsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    private lateinit var sharedPref: SharedPreferences

    private lateinit var portal: String
    private lateinit var categories: List<String>

    companion object {
        val daumCategories = listOf("뉴스", "연예", "스포츠")
        val naverCategories = listOf("정치", "경제", "사회", "생활/문화", "세계", "IT/과학")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        portal = sharedPref.getString(Constants.SELECTED_PORTAL, Constants.NAVER)
        categories = when (portal) {
            Constants.DAUM -> daumCategories
            else -> naverCategories
        }

        setSupportActionBar(toolbar)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        container.adapter = mSectionsPagerAdapter

        for (category in categories) {
            tabs.addTab(tabs.newTab().setText(category))
        }

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }

    // SharedPreference 내용이 변경될 때마다 호출되는 리스너
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        categories = when (p0?.getString(p1, Constants.NAVER)) {
            Constants.DAUM -> daumCategories
            else -> naverCategories
        }

        // 탭은 제거하지만 뷰페이저 내용은 안지워지는 듯 함
        tabs.removeAllTabs()
        mSectionsPagerAdapter!!.notifyDataSetChanged() // 모든탭 제거 후 notify 해주지 않으면 오류 발생

        for (category in categories) {
            tabs.addTab(tabs.newTab().setText(category))
        }

        mSectionsPagerAdapter!!.notifyDataSetChanged() // 새로 탭을 추가했기 때문에 호출해주어야 함
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_news, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        // 어떤 portal 의 뉴스를 보여줄 지 옵션메뉴에서 설정
        portal = when (id) {
            R.id.daum -> Constants.DAUM
            else -> Constants.NAVER
        }

        // shared preference 내용 변경
        with(sharedPref.edit()) {
            putString(Constants.SELECTED_PORTAL, portal)
            apply()
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        sharedPref.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        sharedPref.unregisterOnSharedPreferenceChangeListener(this)
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

            return rootView
        }

        /* 다음 -> 네이버로 변경시 뷰페이저 0~2 번째 내용이 비어있음 (다음 크롤링 구현이 안되어있기 때문에)
         * 반대의 경우에는 네이버의 내용이 그대로 보여짐. (프래그먼트에 정보가 유지되는 것 같음. 페이저 어댑터의 내용은 지울 수 없을까?)
         * 그래서 탭을 옮길 때 마다 새롭게 크롤링을 하도록 구현. 그러나 당연한 버벅거림이 발생 */
        override fun onResume() {
            super.onResume()
            val task = NaverNewsAsyncTask(arguments!!.getString(CATEGORY), this)
            task.execute()
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
            for (element in elements) {
                if (element.parents()[0].select("h5").text() == category) {
                    for ((j, child) in element.children().withIndex()) {
                        val title = child.select("a").attr("title")
                        val url = "https://news.naver.com/" + child.select("a").attr("href")
                        data.add(NewsItem(category, j + 1, title, url))
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
