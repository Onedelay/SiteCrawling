package com.onedelay.sitecrawling

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val adapter = ImageListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = GridLayoutManager(this, 3)
        recyclerview.adapter = adapter
        adapter.setItems(listOf(
                ImageItem("이미지1", "http://pet.chosun.com/images/news/healthchosun_pet_201708/20170831114133_1339_3748_5217.jpg"),
                ImageItem("이미지2", "http://www.gettyimagesgallery.com/picture-library/image.aspx?id=2380"),
                ImageItem("이미지3", "http://www.gettyimagesgallery.com/picture-library/image.aspx?id=2332"),
                ImageItem("이미지4", "http://www.gettyimagesgallery.com/picture-library/image.aspx?id=7992"),
                ImageItem("이미지5", "http://www.gettyimagesgallery.com/picture-library/image.aspx?id=7258"),
                ImageItem("이미지6", "http://www.gettyimagesgallery.com/picture-library/image.aspx?id=7992"),
                ImageItem("이미지7", "http://www.gettyimagesgallery.com/picture-library/image.aspx?id=2332"),
                ImageItem("이미지8", "http://www.gettyimagesgallery.com/picture-library/image.aspx?id=2380")
        ))
    }
}
