package works.newsapi.arief.presentation.newssource

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_news_sources.*
import works.newsapi.arief.AriefApplication
import works.newsapi.arief.R
import works.newsapi.arief.common.extension.makeGone
import works.newsapi.arief.common.extension.makeVisible
import works.newsapi.arief.data.server.entity.response.NewsSourcesResponse
import works.newsapi.arief.presentation.newssource.adapter.NewsSourcesAdapter
import works.newsapi.arief.service.NavigationService

class NewsSourcesActivity : AppCompatActivity(), NewsSourcesContract.View {

    private val presenter by lazy { AriefApplication.ariefComponent.provideSourceListPresenter() }
    private val navigator by lazy { AriefApplication.ariefComponent.provideNavigationService() }
    private val extraCategory by lazy { intent.getStringExtra(NavigationService.EXTRA_CATEGORIES) }
    private var originalList = listOf<NewsSourcesResponse.Sources>()
    private var filteredList = mutableListOf<NewsSourcesResponse.Sources>()
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private var shouldLoadMore = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_sources)
        supportActionBar?.elevation = 0f
        presenter.attach(this)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun initViews() {

        edittext_search_sources.addTextChangedListener(object : TextWatcher {

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
                            .filter { it.name!!.contains(keyword, ignoreCase = true) }
                            .toMutableList()
                            .let {
                                getListAdapter()?.setData(it)
                                if (it.isNullOrEmpty())
                                    Toast.makeText(applicationContext, "Tidak menemukan pencarian", Toast.LENGTH_SHORT).show()
                            }
                }
            }
        })

        recyclerview_list_sources.apply {
            layoutManager = LinearLayoutManager(this@NewsSourcesActivity)
            adapter = NewsSourcesAdapter { _, it ->
                onClick(it.id)
            }
                scrollListener = object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (shouldLoadMore) {
                            val lastVisibleItem = (layoutManager as? LinearLayoutManager)?.findLastCompletelyVisibleItemPosition()
                                    ?: DEFAULT_POSITION
                            val totalItemCount = recyclerView!!.layoutManager!!.itemCount
                            if (totalItemCount == lastVisibleItem + 1) {
                                recyclerview_list_sources.removeOnScrollListener(scrollListener)
                            }
                        }
                    }
                }
                addOnScrollListener(scrollListener)
            }.also {
                presenter.fetchNewsSourcesData(extraCategory)
            }
    }

    override fun onError(it: Throwable?) {
        recyclerview_list_sources.makeGone()
        btn_retry_connection_sources.makeVisible()
        btn_retry_connection_sources.setOnClickListener {
            presenter.fetchNewsSourcesData(extraCategory)
        }
    }

    override fun showListData(it: List<NewsSourcesResponse.Sources>) {
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
        shimmer_loading_sources.makeGone()
    }

    override fun showLoading() {
        shimmer_loading_sources.makeVisible()
        btn_retry_connection_sources.makeGone()
    }

    private fun onClick(source: String) {
        navigator.startArticlesActivity(this, source)
    }

    private fun getListAdapter(): NewsSourcesAdapter? = recyclerview_list_sources?.adapter as? NewsSourcesAdapter

    companion object {
        const val DEFAULT_POSITION = 0
    }
}