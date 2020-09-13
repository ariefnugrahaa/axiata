package works.newsapi.arief.domain.usecase

import io.reactivex.Observable
import works.newsapi.arief.common.base.usecase.BaseUseCase
import works.newsapi.arief.data.server.ApiServiceManager
import works.newsapi.arief.data.server.entity.response.NewsSourcesResponse

class GetNewsSourcesUseCase(private val repository: ApiServiceManager) : BaseUseCase<String, Int, Observable<List<NewsSourcesResponse.Sources>>> {

    override fun execute(parameters: String, secondParameters: Int): Observable<List<NewsSourcesResponse.Sources>> {
        return repository.emittedNewsSources(parameters, secondParameters)
            .map { it.sources }
    }
}