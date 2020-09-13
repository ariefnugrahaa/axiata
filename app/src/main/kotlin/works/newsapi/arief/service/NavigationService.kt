package works.newsapi.arief.service

import android.app.Activity
import android.content.Intent
import works.newsapi.arief.presentation.newsarticles.NewsArticlesActivity
import works.newsapi.arief.presentation.newsarticles.details.DetailsArticlesActivity
import works.newsapi.arief.presentation.newscategories.NewsCategoriesActivity
import works.newsapi.arief.presentation.newssource.NewsSourcesActivity

class NavigationService {

    fun startNewsCategoriesActivity(activity: Activity) {
        activity.run {
            startActivity(Intent(this, NewsCategoriesActivity::class.java))
            finish()
        }
    }

    fun startSourcesActivity(activity: Activity, categories: String) {
        activity.run {
            startActivity(Intent(this, NewsSourcesActivity::class.java).apply {
                putExtra(EXTRA_CATEGORIES, categories)
            })
        }
    }

    fun startArticlesActivity(activity: Activity, articles: String) {
        activity.run {
            startActivity(Intent(this, NewsArticlesActivity::class.java).apply {
                putExtra(EXTRA_SOURCE, articles)
            })
        }
    }

    fun startWebViewActivity(activity: Activity, url: String) {
        activity.run {
            startActivity(Intent(this, DetailsArticlesActivity::class.java).apply {
                putExtra(EXTRA_WEBVIEW, url)
            })
        }
    }

    companion object {
        const val EXTRA_IMAGE = "intent.image"
        const val EXTRA_CONTENT = "intent.content"
        const val EXTRA_CATEGORIES = "intent.categories"
        const val EXTRA_SOURCE = "intent.source"
        const val EXTRA_WEBVIEW = "intent.webview"
    }
}