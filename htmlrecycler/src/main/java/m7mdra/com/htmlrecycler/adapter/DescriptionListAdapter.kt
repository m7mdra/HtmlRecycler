package m7mdra.com.htmlrecycler.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import m7mdra.com.htmlrecycler.R
import m7mdra.com.htmlrecycler.model.DescriptionList
import m7mdra.com.htmlrecycler.viewholder.DescriptionItemViewHolder

class DescriptionListAdapter(val list: List<DescriptionList>) : RecyclerView.Adapter<DescriptionItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescriptionItemViewHolder {
        return DescriptionItemViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.row_description_title_detail, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DescriptionItemViewHolder, position: Int) {
        val description = list[position]
        holder.title.text = description.descriptionTitle
        holder.description.text = description.descriptionData
    }

}
