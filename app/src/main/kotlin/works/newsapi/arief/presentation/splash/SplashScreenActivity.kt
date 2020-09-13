package works.newsapi.arief.presentation.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import works.newsapi.arief.AriefApplication
import works.newsapi.arief.R

class SplashScreenActivity : AppCompatActivity() {

    private val navigation by lazy { AriefApplication.ariefComponent.provideNavigationService() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        initSplash()
    }

    override fun onBackPressed() = Unit

    private fun initSplash(){
        val r: Runnable = Runnable { navigation.startNewsCategoriesActivity(this@SplashScreenActivity) }
        Handler().postDelayed(r, 4000)
    }
}
