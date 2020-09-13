package works.newsapi.arief.data.server.entity.response

import com.google.gson.annotations.SerializedName

data class NewsCategoriesResponse(
        @SerializedName("id")
        val id: Int,
        @SerializedName("categories")
        val categories: String,
        @SerializedName("categoriesName")
        val categoriesName: String,
        @SerializedName("categoriesName")
        val imageCategory: Int
)