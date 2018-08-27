package m7mdra.com.htmlrecycler.source

import android.content.Context
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class AssetSource(private val context: Context, private val uri: String) : Source {
    override fun get(): Document {
        return Jsoup.parse(context.assets.open(uri).bufferedReader().readLines().joinToString { it })
    }
}