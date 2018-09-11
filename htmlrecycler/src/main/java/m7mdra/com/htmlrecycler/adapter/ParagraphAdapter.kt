package m7mdra.com.htmlrecycler.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import m7mdra.com.htmlrecycler.R
import m7mdra.com.htmlrecycler.elements.*
import m7mdra.com.htmlrecycler.htmlfiy
import m7mdra.com.htmlrecycler.viewholder.UnknownViewHolder
import m7mdra.com.htmlrecycler.viewholder.paragraph.*

class ParagraphAdapter(private val list: List<Paragraph>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        UNDERLINE ->
            UnderLineViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_paragraph_item, parent, false))
        ANCHOR_LINK ->
            AnchorLinkViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_paragraph_item, parent, false))
        BODY ->
            BodyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_paragraph_item, parent, false))
        BOLD ->
            BoldViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_paragraph_item, parent, false))
        EMPHASIZES ->
            EmphasizesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_paragraph_item, parent, false))
        UNKNOWN ->
            UnknownViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_paragraph_item, parent, false))
        else ->
            UnknownViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_paragraph_item, parent, false))

    }


    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val paragraph = list[position]
        when (holder) {
            is EmphasizesViewHolder -> holder.paragraphText.text = htmlfiy("<i>${(paragraph as Emphasizes).text}</i>")
            is BodyViewHolder -> holder.paragraphText.text = (paragraph as Body).bodyText
            is AnchorLinkViewHolder -> {
                val anchorLink = paragraph as AnchorLinkInParagraph
                holder.paragraphText.apply {
                    text = anchorLink.text
                    setTextColor(Color.BLUE)
                    setOnClickListener {
                        Toast.makeText(holder.itemView.context, anchorLink.url, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            is UnderLineViewHolder ->
                holder.paragraphText.text = htmlfiy("<u>${(paragraph as UnderLine).text}</u>")

            is BoldViewHolder->
                holder.paragraphText.text = htmlfiy("<b>${(paragraph as Bold).text}</b>")

        }

    }

    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is Body -> BODY
        is UnderLine -> UNDERLINE
        is AnchorLinkInParagraph -> ANCHOR_LINK
        is Bold -> BOLD
        is Emphasizes -> EMPHASIZES

        else -> UNKNOWN
    }

    companion object {
        const val UNKNOWN = 0

        const val ANCHOR_LINK = 1
        const val BODY = 2
        const val BOLD = 3
        const val EMPHASIZES = 4
        const val UNDERLINE = 5

    }
}