package works.newsapi.arief.presentation.newscategories.constant

import works.newsapi.arief.R
import works.newsapi.arief.data.server.entity.response.NewsCategoriesResponse

object ConstantNewsCategories {
    fun listNewsCategories(): MutableList<NewsCategoriesResponse> {
        return mutableListOf(
                NewsCategoriesResponse(0, "general", "General", R.drawable.ic_general),
                NewsCategoriesResponse(1, "entertainment", "Entertainment", R.drawable.ic_entertainment),
                NewsCategoriesResponse(2, "health", "Health", R.drawable.ic_health),
                NewsCategoriesResponse(3, "science", "Science", R.drawable.ic_science),
                NewsCategoriesResponse(4, "sports", "Sports", R.drawable.ic_sports),
                NewsCategoriesResponse(5, "technology", "Technology", R.drawable.ic_technology)
        )
    }
}