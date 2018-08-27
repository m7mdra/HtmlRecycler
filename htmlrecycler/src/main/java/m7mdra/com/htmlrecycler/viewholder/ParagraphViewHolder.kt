package m7mdra.com.htmlrecycler.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import m7mdra.com.htmlrecycler.R

class ParagraphViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val paragraphText = view.findViewById<TextView>(R.id.paragraph)
}