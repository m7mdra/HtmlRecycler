package m7mdra.com.htmlrecycler.extractor

import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.Spanned
import org.jsoup.nodes.Element

@Suppress("DEPRECATION")
class ParagraphExtractor(val element: Element) :Extractor<Spanned>(element) {
    override fun extract(): Spanned {
      return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
          Html.fromHtml(element.html(),Html.FROM_HTML_MODE_LEGACY)
       else
          Html.fromHtml(element.html())

    }
}
