package works.newsapi.arief.data.server.entity.response

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class NewsArticlesResponse(
    @SerializedName("articles")
    val articles: MutableList<Article>,
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?) {

    data class Article(
        @SerializedName("author")
        @Expose
        val author: String?,
        @SerializedName("content")
        @Expose
        val content: String?,
        @SerializedName("description")
        @Expose
        val description: String,
        @SerializedName("publishedAt")
        @Expose
        val publishedAt: String?,
        @SerializedName("source")
        @Expose
        val source: Source?,
        @SerializedName("title")
        @Expose
        val title: String?,
        @SerializedName("url")
        @Expose
        val url: String?,
        @SerializedName("urlToImage")
        @Expose
        val urlToImage: String?
    ) {

        data class Source(
            @SerializedName("id")
            val id: String?,
            @SerializedName("name")
            val name: String?
        )
    }
}