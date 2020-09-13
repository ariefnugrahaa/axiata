package works.newsapi.arief.presentation.newsarticles

import works.newsapi.arief.data.server.entity.response.NewsArticlesResponse
import works.newsapi.arief.data.server.entity.response.NewsSourcesResponse

interface NewsArticlesContract {
    interface View {
        fun initViews()
        fun onError(it: Throwable?)
        fun showListData(it: List<NewsArticlesResponse.Article>)
        fun hideLoading()
        fun showLoading()
    }

    interface Presenter {
        fun fetchNewsArticlesData(source: String)
        fun fetchNewsArticlesDataOnMore(source: String)
    }
}