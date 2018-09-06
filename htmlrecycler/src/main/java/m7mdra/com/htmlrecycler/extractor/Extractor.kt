package m7mdra.com.htmlrecycler.extractor

import org.jsoup.nodes.Element

abstract class Extractor<out T>(private val element: Element) {
    abstract fun extract(): T
    init {
        requireNotNull(element){
            "Element can not be null"
        }
    }


}