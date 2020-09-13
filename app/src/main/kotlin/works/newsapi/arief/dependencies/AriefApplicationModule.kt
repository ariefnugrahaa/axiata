package works.newsapi.arief.dependencies

import android.content.Context
import dagger.Module
import dagger.Provides
import works.newsapi.arief.AriefApplication
import javax.inject.Singleton

@Module
class AriefApplicationModule(private val ariefApplication: AriefApplication) {

    @Singleton
    @Provides
    fun provideApplicationContext(): Context {
        return ariefApplication
    }
}