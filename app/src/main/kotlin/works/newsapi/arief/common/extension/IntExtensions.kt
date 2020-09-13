package works.newsapi.arief.common.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Int.timestampToDate(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(this.toLong()))
}
