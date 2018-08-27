package m7mdra.com.htmlrecycler.extractor

import org.jsoup.nodes.Element

class ImageExtractor(val element: Element) : Extractor<String>(element) {
    override fun extract(): String {
        val src = element.attr("src")
        val absSrc = element.attr("abs:src")
        return if (absSrc.isNotEmpty())
            absSrc
        else
            src
    }
}