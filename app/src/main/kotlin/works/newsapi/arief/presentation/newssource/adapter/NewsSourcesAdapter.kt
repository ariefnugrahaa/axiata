package works.newsapi.arief.presentation.newssource.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_news_sources_list.view.*
import works.newsapi.arief.R
import works.newsapi.arief.common.base.adapter.BaseAdapter
import works.newsapi.arief.data.server.entity.response.NewsSourcesResponse

class NewsSourcesAdapter(private val onClickListener: (position: Int, data: NewsSourcesResponse.Sources) -> Unit) :
    BaseAdapter<NewsViewHolder, NewsSourcesResponse.Sources>() {

    private var dataList = mutableListOf<NewsSourcesResponse.Sources>()

    override fun getItemCount(): Int = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_news_sources_list, parent, false)
        return NewsViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    override fun addAllData(data: MutableList<NewsSourcesResponse.Sources>) {
        this.dataList.addAll(data)
        this.notifyDataSetChanged()
    }

    override fun addData(data: NewsSourcesResponse.Sources) {
        this.dataList.add(data)
        this.notifyDataSetChanged()
    }

    override fun getDataAt(position: Int): NewsSourcesResponse.Sources = dataList[position]

    override fun getAllData(): MutableList<NewsSourcesResponse.Sources> = dataList

    override fun setData(data: MutableList<NewsSourcesResponse.Sources>) {
        this.dataList = data
        this.notifyDataSetChanged()
    }
}

class NewsViewHolder(
    viewItem: View,
    private val onClickListener: (position: Int, data: NewsSourcesResponse.Sources) -> Unit
) : RecyclerView.ViewHolder(viewItem) {
    fun bindData(data: NewsSourcesResponse.Sources) {
        itemView.textview_newssources.text = data.name
        itemView.textview_description_sources.text = data.description
        itemView.linearlayout_newssources.setOnClickListener {
            onClickListener(adapterPosition, data)
        }
    }
}