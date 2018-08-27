package m7mdra.com.htmlrecycler.extractor

import org.jsoup.nodes.Element

class AnchorLinkExtractor(val element: Element) : Extractor<Pair<String, String>>(element) {
    override fun extract(): Pair<String, String> {
        return element.text() to element.attr("href")
    }
}