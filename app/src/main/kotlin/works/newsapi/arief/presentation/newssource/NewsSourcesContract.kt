package works.newsapi.arief.presentation.newssource

import works.newsapi.arief.data.server.entity.response.NewsSourcesResponse

interface NewsSourcesContract {
    interface View {
        fun initViews()
        fun onError(it: Throwable?)
        fun showListData(it: List<NewsSourcesResponse.Sources>)
        fun hideLoading()
        fun showLoading()
    }

    interface Presenter {
        fun fetchNewsSourcesData(category: String)
        fun fetchNewsSourceDataOnMore(category: String)
    }
}