package m7mdra.com.htmlrecycler.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import m7mdra.com.htmlrecycler.R

class DescriptionItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.title)
    val description: TextView = view.findViewById(R.id.description)
}