package m7mdra.com.htmlrecycler.extractor

import org.jsoup.nodes.Element

class VideoExtractor(val element: Element) : Extractor<String>(element) {
    override fun extract(): String {
        return if (element.children().size > 0)
            element.child(0).attr("abs:src")
        else element.attr("abs:src")
    }

}
