package works.newsapi.arief.presentation.newscategories.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news_categories_list.view.*
import works.newsapi.arief.R
import works.newsapi.arief.common.base.adapter.BaseAdapter
import works.newsapi.arief.data.server.entity.response.NewsCategoriesResponse

class NewsCategoriesAdapter(private val onClickListener: (position: Int, data:  NewsCategoriesResponse) -> Unit) : BaseAdapter<NewsCategoriesViewHolder, NewsCategoriesResponse>() {

    private var dataList = mutableListOf<NewsCategoriesResponse>()

    override fun getItemCount(): Int = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsCategoriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_news_categories_list, parent, false)
        return NewsCategoriesViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: NewsCategoriesViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    override fun addAllData(data: MutableList<NewsCategoriesResponse>) {
        this.dataList.addAll(data)
        this.notifyDataSetChanged()
    }

    override fun addData(data: NewsCategoriesResponse) {
        this.dataList.add(data)
        this.notifyDataSetChanged()
    }

    override fun getDataAt(position: Int): NewsCategoriesResponse = dataList[position]

    override fun getAllData(): MutableList<NewsCategoriesResponse> = dataList

    override fun setData(data: MutableList<NewsCategoriesResponse>) {
        this.dataList = data
        this.notifyDataSetChanged()
    }
}

class NewsCategoriesViewHolder(
    viewItem: View,
    private val onClickListener: (position: Int, data: NewsCategoriesResponse) -> Unit
) : RecyclerView.ViewHolder(viewItem) {

    @SuppressLint("UseCompatLoadingForDrawables")
    fun bindData(data: NewsCategoriesResponse) {
        itemView.imageview_categories.setImageDrawable(itemView.resources.getDrawable(data.imageCategory))
        itemView.textview_newscategories.text = data.categoriesName
        itemView.linearlayout_newscategories.setOnClickListener {
            onClickListener(adapterPosition, data)
        }
    }
}