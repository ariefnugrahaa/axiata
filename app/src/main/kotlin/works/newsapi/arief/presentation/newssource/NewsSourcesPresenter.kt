package works.newsapi.arief.presentation.newssource

import io.reactivex.Observable
import works.newsapi.arief.common.base.presenter.BasePresenter
import works.newsapi.arief.data.server.entity.response.NewsSourcesResponse
import works.newsapi.arief.domain.usecase.GetNewsSourcesUseCase

class NewsSourcesPresenter(private val getNewsSourcesUseCase: GetNewsSourcesUseCase) :
    BasePresenter<NewsSourcesContract.View>(), NewsSourcesContract.Presenter {

    private var page: Int = 1

    override fun onAttach() {
        super.onAttach()
        view().initViews()
    }

    override fun fetchNewsSourcesData(category: String) {
        observe {
            Observable.just(view().showLoading())
                    .flatMap { subscribeOnIoSchedulers(getNewsSourcesUseCase.execute(category, page)) }
                    .doFinally { view().hideLoading() }
                    .subscribe(
                            { onFetchNewsSourcesSuccess(it) },
                            { onFetchNewsSourcesFailed(it) }
                    )
        }
    }

    override fun fetchNewsSourceDataOnMore(category: String) {
        page += 1
        observe {
            subscribeOnIoSchedulers(getNewsSourcesUseCase.execute(category,  page))
                    .subscribe(
                            { onFetchNewsSourcesSuccess(it) },
                            { onFetchNewsSourcesFailed(it) }
                    )
        }
    }

    private fun onFetchNewsSourcesSuccess(it: List<NewsSourcesResponse.Sources>) {
        view().showListData(it)
    }

    private fun onFetchNewsSourcesFailed(it: Throwable) {
        view().onError(it)
        page = 0
    }
}