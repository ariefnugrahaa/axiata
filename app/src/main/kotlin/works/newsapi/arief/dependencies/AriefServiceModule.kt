package works.newsapi.arief.dependencies

import dagger.Module
import dagger.Provides
import works.newsapi.arief.service.NavigationService

@Module
class AriefServiceModule {

    @Provides
    fun provideNavigationService() = NavigationService()

}