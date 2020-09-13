package works.newsapi.arief.domain.usecase

import io.reactivex.Observable
import works.newsapi.arief.common.base.usecase.BaseUseCase
import works.newsapi.arief.data.server.ApiServiceManager
import works.newsapi.arief.data.server.entity.response.NewsArticlesResponse

class GetNewsArticlesUseCase(private val repository: ApiServiceManager) : BaseUseCase<String, Int, Observable<List<NewsArticlesResponse.Article>>> {

    override fun execute(parameters: String, secondParameters: Int): Observable<List<NewsArticlesResponse.Article>> {
        return repository.emittedNewsArticles(parameters, secondParameters)
            .map { it.articles }
    }
}