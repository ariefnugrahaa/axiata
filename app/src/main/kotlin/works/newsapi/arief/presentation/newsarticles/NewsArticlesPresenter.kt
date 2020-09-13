package works.newsapi.arief.presentation.newsarticles

import io.reactivex.Observable
import works.newsapi.arief.common.base.presenter.BasePresenter
import works.newsapi.arief.data.server.entity.response.NewsArticlesResponse
import works.newsapi.arief.domain.usecase.GetNewsArticlesUseCase

class NewsArticlesPresenter(private val getNewsArticlesUseCase: GetNewsArticlesUseCase) :
    BasePresenter<NewsArticlesContract.View>(), NewsArticlesContract.Presenter {

    private var page: Int = 1

    override fun onAttach() {
        super.onAttach()
        view().initViews()
    }

    override fun fetchNewsArticlesData(source: String) {
        observe {
            Observable.just(view().showLoading())
                    .flatMap { subscribeOnIoSchedulers(getNewsArticlesUseCase.execute(source, page)) }
                    .doFinally { view().hideLoading() }
                    .subscribe(
                            { onFetchNewsArticlesSuccess(it) },
                            { onFetchNewsSourcesFailed(it) }
                    )
        }
    }

    override fun fetchNewsArticlesDataOnMore(source: String) {
        page += 1
        observe {
            subscribeOnIoSchedulers(getNewsArticlesUseCase.execute(source,  page))
                    .subscribe(
                            { onFetchNewsArticlesSuccess(it) },
                            { onFetchNewsSourcesFailed(it) }
                    )
        }
    }

    private fun onFetchNewsArticlesSuccess(it: List<NewsArticlesResponse.Article>) {
        view().showListData(it)
    }

    private fun onFetchNewsSourcesFailed(it: Throwable) {
        view().onError(it)
        page = 0
    }
}