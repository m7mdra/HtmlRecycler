package m7mdra.com.htmlrecycler.extractor

import org.jsoup.nodes.Element

class AudioExtractor(val e: Element) : Extractor<String>(e) {
    override fun extract(): String {
        return e.attr("src")
    }
}