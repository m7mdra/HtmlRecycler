@file:Suppress("DEPRECATION")

package m7mdra.com.htmlrecycler

import android.text.Html
import android.text.Spanned

fun htmlfiy(html: String): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
        Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY)
    else
        Html.fromHtml(html)
}
