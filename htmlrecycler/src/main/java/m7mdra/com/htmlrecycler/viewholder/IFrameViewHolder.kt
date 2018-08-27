package m7mdra.com.htmlrecycler.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.webkit.WebView
import m7mdra.com.htmlrecycler.R

class IFrameViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val iframeView = view.findViewById<WebView>(R.id.iframe)
}