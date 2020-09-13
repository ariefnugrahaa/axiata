package works.newsapi.arief.presentation.newsarticles.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news_articles_list.view.*
import works.newsapi.arief.R
import works.newsapi.arief.common.base.adapter.BaseAdapter
import works.newsapi.arief.data.server.entity.response.NewsArticlesResponse

class NewsArticlesAdapter(private val onClickListener: (position: Int, data: NewsArticlesResponse.Article) -> Unit) :
    BaseAdapter<NewsViewHolder, NewsArticlesResponse.Article>() {

    private var dataList = mutableListOf<NewsArticlesResponse.Article>()

    override fun getItemCount(): Int = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_news_articles_list, parent, false)
        return NewsViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    override fun addAllData(data: MutableList<NewsArticlesResponse.Article>) {
        this.dataList.addAll(data)
        this.notifyDataSetChanged()
    }

    override fun addData(data: NewsArticlesResponse.Article) {
        this.dataList.add(data)
        this.notifyDataSetChanged()
    }

    override fun getDataAt(position: Int): NewsArticlesResponse.Article = dataList[position]

    override fun getAllData(): MutableList<NewsArticlesResponse.Article> = dataList

    override fun setData(data: MutableList<NewsArticlesResponse.Article>) {
        this.dataList = data
        this.notifyDataSetChanged()
    }
}

class NewsViewHolder(
    viewItem: View,
    private val onClickListener: (position: Int, data: NewsArticlesResponse.Article) -> Unit
) : RecyclerView.ViewHolder(viewItem) {


    fun bindData(data: NewsArticlesResponse.Article) {
        itemView.textview_articles.text = data.source?.name
        itemView.textview_articles_title.text = data.title
        itemView.textview_articles_description.text = data.description
        Picasso.get().load(data.urlToImage).fit().into(itemView.imageview_url_image_articles)
        itemView.linearlayout_main_articles.setOnClickListener {
            onClickListener(adapterPosition, data)
        }
    }
}