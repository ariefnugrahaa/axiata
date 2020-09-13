package works.newsapi.arief.presentation.newsarticles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_news_articles.*
import kotlinx.android.synthetic.main.activity_news_sources.*
import works.newsapi.arief.AriefApplication
import works.newsapi.arief.R
import works.newsapi.arief.common.extension.makeGone
import works.newsapi.arief.common.extension.makeVisible
import works.newsapi.arief.data.server.entity.response.NewsArticlesResponse
import works.newsapi.arief.presentation.newsarticles.adapter.NewsArticlesAdapter
import works.newsapi.arief.presentation.newssource.NewsSourcesActivity.Companion.DEFAULT_POSITION
import works.newsapi.arief.service.NavigationService

class NewsArticlesActivity : AppCompatActivity(), NewsArticlesContract.View {

    private val presenter by lazy { AriefApplication.ariefComponent.provideArticlesListPresenter() }
    private val navigator by lazy { AriefApplication.ariefComponent.provideNavigationService() }
    private val extraSource by lazy { intent.getStringExtra(NavigationService.EXTRA_SOURCE) }
    private var originalList = listOf<NewsArticlesResponse.Article>()
    private var filteredList = mutableListOf<NewsArticlesResponse.Article>()
    private var shouldLoadMore = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_articles)
        supportActionBar?.elevation = 0f
        presenter.attach(this)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun initViews() {

        edittext_search_articles.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) = Unit

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val keyword = p0.toString()
                if (keyword.isBlank()) {
                    getListAdapter()?.setData(originalList.toMutableList())
                    shouldLoadMore = true
                } else {
                    shouldLoadMore = false
                    filteredList = originalList.toMutableList()
                    filteredList
                            .filter { it.title!!.contains(keyword, ignoreCase = true) }
                            .toMutableList()
                            .let {
                                getListAdapter()?.setData(it)
                                if (it.isNullOrEmpty())
                                    Toast.makeText(applicationContext, "Tidak menemukan pencarian", Toast.LENGTH_SHORT).show()
                            }
                }
            }
        })

        recyclerview_list_articles.apply {
            layoutManager = LinearLayoutManager(this@NewsArticlesActivity)
            adapter = NewsArticlesAdapter { _, it ->
                onClick(it.url.toString())
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (shouldLoadMore) {
                        val lastIndex = getListAdapter()?.getAllData()
                                ?.indexOf(getListAdapter()?.getAllData()?.last())
                                ?: DEFAULT_POSITION
                        val lastVisibleItem =
                                (layoutManager as? LinearLayoutManager)?.findLastCompletelyVisibleItemPosition()
                                        ?: DEFAULT_POSITION
                        val lastItem = getListAdapter()?.getAllData()
                                ?.indexOf(
                                        getListAdapter()?.getAllData()
                                                ?.get(if (lastVisibleItem < 0) 0 else lastVisibleItem)
                                )
                                ?: DEFAULT_POSITION
                        if (lastIndex == lastItem) {
                            presenter.fetchNewsArticlesDataOnMore(extraSource)
                        }
                    }
                }
            })
        }.also { presenter.fetchNewsArticlesData(extraSource) }
    }

    override fun onError(it: Throwable?) {
        shimmer_loading_articles.makeGone()
    }

    override fun showListData(it: List<NewsArticlesResponse.Article>) {
        runOnUiThread {
            recyclerview_list_sources?.makeVisible()
            it.toMutableList().let {
                if (getListAdapter()?.itemCount == DEFAULT_POSITION) {
                    getListAdapter()?.setData(it)
                } else {
                    getListAdapter()?.addAllData(it)
                }
            }
            originalList = getListAdapter()?.getAllData().orEmpty()
        }
    }

    override fun hideLoading() {
        shimmer_loading_articles.makeGone()
    }

    override fun showLoading() {
        shimmer_loading_articles.makeVisible()
    }

    private fun onClick(url: String) {
        navigator.startWebViewActivity(this, url)
    }

    private fun getListAdapter(): NewsArticlesAdapter? = recyclerview_list_articles?.adapter as? NewsArticlesAdapter

}