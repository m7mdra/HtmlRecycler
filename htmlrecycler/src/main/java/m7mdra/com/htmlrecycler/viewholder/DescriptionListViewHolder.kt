package m7mdra.com.htmlrecycler.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import m7mdra.com.htmlrecycler.R

class DescriptionListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
}