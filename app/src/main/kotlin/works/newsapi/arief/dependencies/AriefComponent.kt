package works.newsapi.arief.dependencies

import dagger.Component
import works.newsapi.arief.presentation.newsarticles.NewsArticlesPresenter
import works.newsapi.arief.presentation.newssource.NewsSourcesPresenter
import works.newsapi.arief.service.NavigationService
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AriefApplicationModule::class,
    AriefNetworkModule::class,
    AriefMapperModule::class,
    AriefPresenterModule::class,
    AriefUseCaseModule::class,
    AriefServiceModule::class
])

interface AriefComponent {
    fun provideNavigationService(): NavigationService
    fun provideSourceListPresenter(): NewsSourcesPresenter
    fun provideArticlesListPresenter(): NewsArticlesPresenter
}