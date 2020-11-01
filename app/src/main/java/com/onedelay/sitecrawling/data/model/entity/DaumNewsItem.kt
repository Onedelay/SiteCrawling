package com.onedelay.sitecrawling.data.model.entity


data class DaumNewsItem(
        val rank: Int,
        val url: String,
        val title: String,
        val thumbContent: String,
        val thumbImageUrl: String,
        val company: String
)