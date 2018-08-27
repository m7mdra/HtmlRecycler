package m7mdra.com.htmlrecycler.adapter


import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import m7mdra.com.htmlrecycler.elements.ElementType
import m7mdra.com.htmlrecycler.elements.*

abstract class ElementsAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract fun onCreateElement(parent: ViewGroup, elementType: ElementType): RecyclerView.ViewHolder
    abstract fun onBindElement(holder: RecyclerView.ViewHolder, position: Int)
    val elements = mutableListOf<Element>()


    fun addElements(elementList: List<Element>) {
        elements.clear()
        elements.addAll(elementList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("MEGA", "${findElementWith(viewType)}")
        return onCreateElement(parent, findElementWith(viewType))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindElement(holder, position)
    }


    override fun getItemCount(): Int {
        return elements.size
    }

    override fun getItemViewType(position: Int): Int {
        val element = elements[position]
        return when (element) {
            is AnchorLinkElement -> TYPE_ANCHOR_LINK
            is VideoElement -> TYPE_VIDEO
            is BlockQuoteElement -> TYPE_BLOCK_QUOTE
            is Heading1Element -> TYPE_HEADING1
            is Heading2Element -> TYPE_HEADING2
            is Heading3Element -> TYPE_HEADING3
            is Heading4Element -> TYPE_HEADING4
            is Heading5Element -> TYPE_HEADING5
            is Heading6Element -> TYPE_HEADING6
            is ImageElement -> TYPE_IMAGE
            is IFrameElement -> TYPE_IFRMAE
            is AudioElement -> TYPE_AUDIO
            is ParagraphElement -> TYPE_PARAGRAPH
            is UnknownElement -> TYPE_UNKNOWN
            is DescriptionListElement -> TYPE_DESCRIPTION_LIST
            is UnOrderListElement -> TYPE_UNORDERED_LIST
            is OrderListElement -> TYPE_ORDERED_LIST
        }
    }

    companion object {
        const val TYPE_UNKNOWN = -1
        const val TYPE_HEADING1 = 1
        const val TYPE_HEADING2 = 2
        const val TYPE_HEADING3 = 3
        const val TYPE_HEADING4 = 4
        const val TYPE_HEADING5 = 5
        const val TYPE_HEADING6 = 6
        const val TYPE_IFRMAE = 7
        const val TYPE_BLOCK_QUOTE = 8
        const val TYPE_PARAGRAPH = 9
        const val TYPE_ANCHOR_LINK = 10
        const val TYPE_IMAGE = 11
        const val TYPE_VIDEO = 12
        const val TYPE_AUDIO = 13
        const val TYPE_ORDERED_LIST = 14
        const val TYPE_DESCRIPTION_LIST = 15
        const val TYPE_DIV = 16
        const val TYPE_TABLE = 17
        const val TYPE_UNORDERED_LIST = 18
    }

    private fun findElementWith(type: Int): ElementType {
        for (value in ElementType.values()) {
            if (value.ordinal == type)
                return value
        }
        return ElementType.Unknown
    }



}
