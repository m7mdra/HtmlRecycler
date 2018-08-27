package m7mdra.com.htmlrecycler.elements

import m7mdra.com.htmlrecycler.extractor.*
import m7mdra.com.htmlrecycler.model.AnchorLink
import m7mdra.com.htmlrecycler.model.DescriptionList
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class ElementIdentifier(private val element: Element) {
    fun identify(): ElementType {
        return when (element.tagName()) {
            "h1" -> return ElementType.Heading1
            "h2" -> return ElementType.Heading2
            "h3" -> return ElementType.Heading3
            "h4" -> return ElementType.Heading4
            "h5" -> return ElementType.Heading5
            "h6" -> return ElementType.Heading6
            "blockquote" -> return ElementType.BlockQuote
            "p" -> return ElementType.Paragraph
            "iframe" -> return ElementType.IFrame
            "a" -> return ElementType.AnchorLink
            "img" -> return ElementType.Image
            "video" -> return ElementType.Video
            "audio" -> return ElementType.Audio
            "ol" -> return ElementType.OrderedList
            "ul" -> return ElementType.UnorderedList
            "dl" -> return ElementType.DescriptionList
//            "div" -> return ElementType.Div
//            "table" -> return ElementType.Table
            else -> ElementType.Unknown
        }
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
                        elementList.add(UnknownElement())
                    }

                }
            }
        }
    }
}