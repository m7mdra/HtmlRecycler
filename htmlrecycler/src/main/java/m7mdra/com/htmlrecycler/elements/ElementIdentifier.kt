package m7mdra.com.htmlrecycler.elements

import android.util.Log
import m7mdra.com.htmlrecycler.extractor.*
import m7mdra.com.htmlrecycler.model.AnchorLink
import m7mdra.com.htmlrecycler.model.DescriptionList
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class ElementIdentifier(private val element: Element) {
    fun identify(): ElementType = when (element.tagName()) {
        "h1" -> ElementType.Heading1
        "h2" -> ElementType.Heading2
        "h3" -> ElementType.Heading3
        "h4" -> ElementType.Heading4
        "h5" -> ElementType.Heading5
        "h6" -> ElementType.Heading6
        "blockquote" -> ElementType.BlockQuote
        "p" -> ElementType.Paragraph
        "iframe" -> ElementType.IFrame
        "a" -> ElementType.AnchorLink
        "img" -> ElementType.Image
        "video" -> ElementType.Video
        "audio" -> ElementType.Audio
        "ol" -> ElementType.OrderedList
        "ul" -> ElementType.UnorderedList
        "dl" -> ElementType.DescriptionList
        "div" -> ElementType.Div
        "section" -> ElementType.Section
        else -> ElementType.Unknown
    }


    companion object {
        @JvmStatic
        fun extractData(elementList: MutableList<m7mdra.com.htmlrecycler.elements.Element>, elements: Elements) {
            elements.forEach {
                when (ElementIdentifier(it).identify()) {
                    ElementType.Image -> {
                        val imageUrl = ImageExtractor(it).extract()
                        elementList.add(ImageElement(imageUrl))
                    }
                    ElementType.Heading1 -> {
                        val heading = HeadingExtractor(it).extract()
                        elementList.add(Heading1Element(heading))

                    }
                    ElementType.Heading2 -> {
                        val heading = HeadingExtractor(it).extract()
                        elementList.add(Heading2Element(heading))

                    }
                    ElementType.Heading3 -> {
                        val heading = HeadingExtractor(it).extract()
                        elementList.add(Heading3Element(heading))

                    }
                    ElementType.Heading4 -> {
                        val heading = HeadingExtractor(it).extract()
                        elementList.add(Heading4Element(heading))

                    }
                    ElementType.Heading5 -> {
                        val heading = HeadingExtractor(it).extract()
                        elementList.add(Heading5Element(heading))

                    }
                    ElementType.Heading6 -> {
                        val heading = HeadingExtractor(it).extract()
                        elementList.add(Heading6Element(heading))

                    }
                    ElementType.UnorderedList -> {
                        val listExtractor = ListExtractor(it).extract()
                        elementList.add(UnOrderListElement(listExtractor))
                    }
                    ElementType.OrderedList -> {
                        val listExtractor = ListExtractor(it).extract()
                        elementList.add(OrderListElement(listExtractor))
                    }
                    ElementType.Video -> {
                        val videoUrl = VideoExtractor(it).extract()
                        elementList.add(VideoElement(videoUrl))
                    }
                    ElementType.AnchorLink -> {
                        val anchorLink = AnchorLinkExtractor(it).extract()
                        elementList.add(AnchorLinkElement(AnchorLink(anchorLink.first, anchorLink.second)))
                    }
                    ElementType.DescriptionList -> {
                        val extract = DescriptionListExtractor(it).extract()
                        val descriptionList = mutableListOf<DescriptionList>()
                        extract.forEach {
                            descriptionList.add(DescriptionList(it.first, it.second))
                        }
                        val descriptionListElement = DescriptionListElement(descriptionList)
                        elementList.add(descriptionListElement)
                    }
                    ElementType.Div -> extractData(elementList, it.children())
                    ElementType.Paragraph -> {
                        val children = it.children()
                        if (children.size > 0 &&
                                it.getElementsByTag("img").isNotEmpty() ||
                                it.getElementsByTag("audio").isNotEmpty() ||
                                it.getElementsByTag("video").isNotEmpty())
                            extractData(elementList, children)
                        else {
                            val text = it.toString()
                            elementList.add(ParagraphElement(
                                    text.replace("<p>", "")
                                            .replace("</p>", "")
                                            .replace("</strong>", "</b>")
                                            .replace("<strong>", "<b>")
                                            .replace("<em>", "<i>")
                                            .replace("</em>", "</i>")
                                            .replace("<span>", "<u>")
                                            .replace("</span>", "</u>")
                                            .trim()
                                            .replace("\\s{2,}", " ")))

                        }
                    }
                    ElementType.BlockQuote -> {
                        elementList.add(BlockQuoteElement(it.text()))
                    }

                    ElementType.IFrame -> {
                        val frameUrl = IFrameExtractor(it).extract()
                        elementList.add(IFrameElement(frameUrl))
                    }
                    ElementType.Audio -> {
                        val audio = AudioExtractor(it).extract()
                        elementList.add(AudioElement(audio))

                    }
                    ElementType.Unknown -> {
                        Log.d("MEGA", "FOUND UNKNOWN ELEMENT? GREAT help us implement it" + it.tagName())
                        elementList.add(UnknownElement())
                    }
                    ElementType.Section -> extractData(elementList, it.children())
                }
            }
        }
    }
}