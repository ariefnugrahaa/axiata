package works.newsapi.arief.common.base.composer

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {


    protected var disableBackPressed: Boolean = false
    protected var wantExitNow: Boolean = false

    override fun onBackPressed() {
        if (disableBackPressed) {
            return
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            finish()
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}