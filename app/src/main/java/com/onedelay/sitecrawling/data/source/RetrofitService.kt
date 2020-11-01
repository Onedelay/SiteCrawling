package com.onedelay.sitecrawling.data.source

import com.onedelay.sitecrawling.data.model.entity.DaumNewsItem
import com.onedelay.sitecrawling.data.model.entity.IssueItem
import com.onedelay.sitecrawling.data.model.entity.NaverNewsItem
import com.onedelay.sitecrawling.data.model.entity.WeeklyItem
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create(RetrofitService::class.java)
        }
    }

    @GET("naver")
    fun getNaverNews(): Single<List<NaverNewsItem>>

    @GET("daum")
    fun getDaumNews(): Single<List<DaumNewsItem>>

    @GET("naver_issue")
    fun getNaverIssue(): Single<List<IssueItem>>

    @GET("daum_issue")
    fun getDaumIssue(): Single<List<IssueItem>>

    @GET("android_weekly")
    fun getAndroidWeekly(): Single<List<WeeklyItem>>
}