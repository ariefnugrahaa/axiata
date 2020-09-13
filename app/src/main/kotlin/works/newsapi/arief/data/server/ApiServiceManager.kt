package works.newsapi.arief.data.server

import io.reactivex.Observable
import works.newsapi.arief.data.server.entity.response.NewsArticlesResponse
import works.newsapi.arief.data.server.entity.response.NewsResponse
import works.newsapi.arief.data.server.entity.response.NewsSourcesResponse

class ApiServiceManager(private val apiService: ApiService) {

    fun emittedNewsSources(category: String, page: Int): Observable<NewsSourcesResponse> {
        return apiService.getNewsSources(category, page)
    }

    fun emittedNewsArticles(sources: String, page: Int): Observable<NewsArticlesResponse> {
        return apiService.getNewsArticles(sources, page)
    }
}