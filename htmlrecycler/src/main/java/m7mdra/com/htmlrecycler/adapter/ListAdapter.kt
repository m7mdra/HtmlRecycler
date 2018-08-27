package m7mdra.com.htmlrecycler.adapter

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.*
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import m7mdra.com.htmlrecycler.R
import m7mdra.com.htmlrecycler.viewholder.ListItemViewHolder

class ListAdapter(private val type: String, private val list: List<String>) : RecyclerView.Adapter<ListItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_list_item, parent, false))
    }

    private val listType: ListType

    init {
        listType = if (type == "ol")
            ListType.Ordered
        else
            ListType.Unordered
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {

        holder.listItem.text = if (listType == ListType.Unordered)
            "${boldyify("*")} ${list[position]}"
        else
            "${position + 1}- ${list[position]}"
    }

    enum class ListType {
        Ordered, Unordered
    }

    fun boldyify(text: String): Spanned {
        val spannableString = SpannableStringBuilder(text)
        spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }

}
