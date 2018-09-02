package m7mdra.com.htmlrecycler.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.text.method.ScrollingMovementMethod
import android.text.util.Linkify
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import m7mdra.com.htmlrecycler.*
import m7mdra.com.htmlrecycler.elements.*
import m7mdra.com.htmlrecycler.viewholder.*
import me.saket.bettermovementmethod.BetterLinkMovementMethod

class DefaultElementsAdapter(private val context: Context, private val onClick: (Element, Int, View) -> Unit) : ElementsAdapter() {

    override fun onCreateElement(parent: ViewGroup, elementType: ElementType): RecyclerView.ViewHolder {
        return when (elementType) {
            ElementType.Heading1 ->
                Heading1ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_heading1, parent, false))
            ElementType.Heading2 ->
                Heading2ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_heading2, parent, false))
            ElementType.Heading3 ->
                Heading3ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_heading3, parent, false))
            ElementType.Heading4 ->
                Heading4ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_heading4, parent, false))
            ElementType.Heading5 ->
                Heading5ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_heading5, parent, false))
            ElementType.Heading6 ->
                Heading6ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_heading6, parent, false))
            ElementType.IFrame ->
                IFrameViewHolder(LayoutInflater.from(context).inflate(R.layout.row_iframe, parent, false))
            ElementType.Image ->
                ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.row_image, parent, false))
            ElementType.Video ->
                VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.row_video, parent, false))
            ElementType.Audio ->
                AudioViewHolder(LayoutInflater.from(context).inflate(R.layout.row_audio, parent, false))
            ElementType.Paragraph ->
                ParagraphViewHolder(LayoutInflater.from(context).inflate(R.layout.row_paragarph, parent, false))
            ElementType.Div ->
                DivViewHolder(LayoutInflater.from(context).inflate(R.layout.row_paragarph, parent, false))
            ElementType.BlockQuote ->
                BlockQuoteViewHolder(LayoutInflater.from(context).inflate(R.layout.row_blockquote, parent, false))
            ElementType.Unknown ->
                EmptyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_empty, parent, false))
            ElementType.AnchorLink ->
                AnchorLinkViewHolder(LayoutInflater.from(context).inflate(R.layout.row_anchor_link, parent, false))
            ElementType.OrderedList ->
                OrderedListViewHolder(LayoutInflater.from(context).inflate(R.layout.row_list, parent, false))
            ElementType.UnorderedList ->
                UnOrderedListViewHolder(LayoutInflater.from(context).inflate(R.layout.row_list, parent, false))
            ElementType.DescriptionList ->
                DescriptionListViewHolder(LayoutInflater.from(context).inflate(R.layout.row_list, parent, false))
            else ->
                EmptyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_empty, parent, false))
        }
    }

    override fun onBindElement(holder: RecyclerView.ViewHolder, position: Int) {
        val element = elements[position]
        holder.itemView.setOnClickListener {
            onClick(element, holder.adapterPosition, it)
        }
        when (holder) {
            is Heading1ViewHolder -> {
                val heading1Element = element as Heading1Element
                holder.headingView.text = heading1Element.text
            }
            is Heading2ViewHolder -> {
                val heading1Element = element as Heading2Element
                holder.headingView.text = heading1Element.text
            }
            is Heading3ViewHolder -> {

                val heading1Element = element as Heading3Element
                holder.headingView.text = heading1Element.text
            }
            is Heading4ViewHolder -> {

                val heading1Element = element as Heading4Element
                holder.headingView.text = heading1Element.text
            }
            is Heading5ViewHolder -> {
                val heading1Element = element as Heading5Element
                holder.headingView.text = heading1Element.text
            }
            is Heading6ViewHolder -> {
                val heading1Element = element as Heading6Element
                holder.headingView.text = heading1Element.text
            }
            is ParagraphViewHolder -> {
                val paragraphElement = element as ParagraphElement
                val textView = holder.paragraphText
                textView.text = htmlfiy(paragraphElement.text)

            }
            is AnchorLinkViewHolder -> {
                val anchorLinkElement = element as AnchorLinkElement
                val anchorUrl = anchorLinkElement.anchorUrl
                holder.anchorTextView.text = anchorUrl.displayText
                holder.anchorTextView.setOnClickListener {
                    Toast.makeText(context, anchorUrl.anchorUrl, Toast.LENGTH_SHORT).show()
                }
            }
            is ImageViewHolder -> {
                Picasso.get().load((element as ImageElement).ImageUrl)
                        .placeholder(ColorDrawable(Color.parseColor("#EFF0EE")))
                        .error(ColorDrawable(Color.parseColor("#EFF0EE")))
                        .into(holder.image)
            }
            is BlockQuoteViewHolder -> {
                holder.blockQuoteTextView.text = (element as BlockQuoteElement).text
            }
            is OrderedListViewHolder -> {
                holder.recyclerView.apply {
                    val itemList = (element as OrderListElement).list
                    adapter = ListAdapter(itemList.first, itemList.second)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
            }
            is UnOrderedListViewHolder -> {
                holder.recyclerView.apply {
                    val itemList = (element as UnOrderListElement).list
                    adapter = ListAdapter(itemList.first, itemList.second)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
            }
            is DescriptionListViewHolder -> {
                holder.recyclerView.apply {
                    val descriptionList = (element as DescriptionListElement).descriptionList
                    adapter = DescriptionListAdapter(descriptionList)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
            }
            is VideoViewHolder -> {
                holder.videoView.apply {
                    val videoElement = element as VideoElement
                    val parse = Uri.parse(videoElement.videoSourceUrl)
                    setVideoURI(parse)
                    start()
                }
            }
            is AudioViewHolder -> {
                holder.videoView.apply {
                    val parse = Uri.parse((element as AudioElement).audioSourceUrl)
                    setVideoURI(parse)
                    start()
                }

            }
            is IFrameViewHolder -> {
                holder.iframeView.loadUrl((element as IFrameElement).url)
                holder.iframeView.settings.javaScriptEnabled = true
            }

        }
    }
}
