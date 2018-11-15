package com.onedelay.sitecrawling.news

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface RetrofitService {
    companion object {
        fun create(): RetrofitService {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            // https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor
            val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .connectTimeout(10, TimeUnit.SECONDS)   // connect timeout
                    .readTimeout(30, TimeUnit.SECONDS)      // socket timeout
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl("https://onedelay-crawler-server.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create(RetrofitService::class.java)
        }
    }

    @GET("naver")
    fun getNaverNews(): Call<List<NewsItem>>

    @GET("daum")
    fun getDaumNews(): Call<List<NewsItem>>
}