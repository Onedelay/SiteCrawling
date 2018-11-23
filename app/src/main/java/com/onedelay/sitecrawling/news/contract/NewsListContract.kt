package com.onedelay.sitecrawling.news.contract

import com.onedelay.sitecrawling.news.model.NewsItem

interface NewsListContract {

    /**
     * 프래그먼트가 구현할 인터페이스
     * presenter 에서 조작
     * */
    interface View {
        /**
         * 서버로부터 받은 결과를 프래그먼트 어댑터에 set
         */
        fun receiveNewsList(items: List<NewsItem>?)

        /**
         * 뉴스 아이템 클릭 시 새로운 브라우저 열기
         */
        fun openNewBrowser(url: String)

        fun hideProgress()
        fun showError()
    }

    /**
     * 프리젠터가 구현할 인터페이스
     * view 에서 조작
     */
    interface UserActions {
        /**
         * 서버에 크롤링 요청
         */
        fun requestServer()

        /**
         * 뉴스 아이템 선택
         */
        fun clickNewsItem(item: NewsItem)
    }
}