package works.newsapi.arief.data.server

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import works.newsapi.arief.data.server.entity.response.NewsArticlesResponse
import works.newsapi.arief.data.server.entity.response.NewsResponse
import works.newsapi.arief.data.server.entity.response.NewsSourcesResponse

interface ApiService {

    @GET(NEWS)
    fun getNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 5,
        @Query("country") country: String = "id",
        @Query("category") category: String = "technology"
    ): Observable<NewsResponse>

    @GET(NEWS_SOURCES)
    fun getNewsSources(
            @Query("category") category: String,
            @Query("page") page: Int,
            @Query("pageSize") pageSize: Int = 5
    ): Observable<NewsSourcesResponse>

    @GET(NEWS_ARTICLES)
    fun getNewsArticles(
            @Query("sources") sources: String,
            @Query("page") page: Int,
            @Query("pageSize") pageSize: Int = 5
    ): Observable<NewsArticlesResponse>

    private companion object {
        const val NEWS = "v2/top-headlines"
        const val NEWS_SOURCES = "v2/sources"
        const val NEWS_ARTICLES = "v2/top-headlines"
    }
}