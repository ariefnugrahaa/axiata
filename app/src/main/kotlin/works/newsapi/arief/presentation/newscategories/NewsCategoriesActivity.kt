package works.newsapi.arief.presentation.newscategories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_news_categories.*
import works.newsapi.arief.AriefApplication
import works.newsapi.arief.R
import works.newsapi.arief.presentation.newscategories.adapter.NewsCategoriesAdapter
import works.newsapi.arief.presentation.newscategories.constant.ConstantNewsCategories.listNewsCategories

class NewsCategoriesActivity : AppCompatActivity() {

    private val navigator by lazy { AriefApplication.ariefComponent.provideNavigationService() }
    lateinit var adapter: NewsCategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_categories)
        initView()
    }

    private fun initView() {
        recyclerview_list_newscategories.apply {
            layoutManager = LinearLayoutManager(this@NewsCategoriesActivity)
            adapter = NewsCategoriesAdapter { _, it ->
                onClick(it.categories)
            }.also { it.addAllData(listNewsCategories())
            }
        }
    }

    private fun onClick(categories: String) {
        navigator.startSourcesActivity(this, categories)
    }
}