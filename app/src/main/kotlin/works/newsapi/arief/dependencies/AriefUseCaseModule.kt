package works.newsapi.arief.dependencies

import dagger.Module
import dagger.Provides
import works.newsapi.arief.data.server.ApiServiceManager
import works.newsapi.arief.domain.usecase.GetNewsArticlesUseCase
import works.newsapi.arief.domain.usecase.GetNewsSourcesUseCase
import javax.inject.Singleton

@Module
class AriefUseCaseModule {

    @Singleton
    @Provides
    fun provideGetNewsSourcesUseCase(apiServiceManager: ApiServiceManager) =
            GetNewsSourcesUseCase(apiServiceManager)

    @Singleton
    @Provides
    fun provideGetNewsArticlesUseCase(apiServiceManager: ApiServiceManager) =
            GetNewsArticlesUseCase(apiServiceManager)
}