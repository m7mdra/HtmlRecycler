package m7mdra.com.htmlrecycler.extractor

import org.jsoup.nodes.Element


class ListExtractor(private val element: Element) : Extractor<Pair<String, List<String>>>(element) {
    override fun extract(): Pair<String, List<String>> {
        return element.tagName() to element.children().map { it.text() }

    }
}
