package works.newsapi.arief.dependencies

import dagger.Module
import dagger.Provides
import works.newsapi.arief.domain.usecase.GetNewsArticlesUseCase
import works.newsapi.arief.domain.usecase.GetNewsSourcesUseCase
import works.newsapi.arief.presentation.newsarticles.NewsArticlesPresenter
import works.newsapi.arief.presentation.newssource.NewsSourcesPresenter
import javax.inject.Singleton

@Module
class AriefPresenterModule {

    @Singleton
    @Provides
    fun provideNewsSourcesPresenter(getNewsSourceUseCase: GetNewsSourcesUseCase) =
            NewsSourcesPresenter(getNewsSourceUseCase)

    @Singleton
    @Provides
    fun provideNewsArticlesPresenter(getNewsArticlesUseCase: GetNewsArticlesUseCase) =
            NewsArticlesPresenter(getNewsArticlesUseCase)

}