package com.onedelay.sitecrawling.image

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.onedelay.sitecrawling.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import java.io.IOException

class MainActivity : AppCompatActivity(), ImageAsyncTask.OnTaskComplete {
    private val adapter = ImageListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = GridLayoutManager(baseContext, 3)
        recyclerview.adapter = adapter

        val task = ImageAsyncTask(this)
        task.execute()
    }

    override fun onTaskComplete(list: List<ImageItem>) {
        adapter.setItems(list)
    }
}

class ImageAsyncTask(private val listener: OnTaskComplete) : AsyncTask<Void, Void, List<ImageItem>>() {

    interface OnTaskComplete {
        fun onTaskComplete(list: List<ImageItem>)
    }

    override fun doInBackground(vararg p0: Void?): List<ImageItem> {
        val data = ArrayList<ImageItem>()
        try {
            val doc = Jsoup.connect("http://www.gettyimagesgallery.com/collections/archive/slim-aarons.aspx").get()
            val elements = doc.select("div.gallery-item-group.exitemrepeater")
            for (element in elements) {
                data.add(ImageItem(
                        element.select("div.gallery-item-caption").text().trim() + "\n", // 이미지 제목
                        element.select("img").attr("abs:src").trim()))              // 이미지 썸네일
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return data
    }

    override fun onPostExecute(result: List<ImageItem>) {
        listener.onTaskComplete(result)
    }
}
