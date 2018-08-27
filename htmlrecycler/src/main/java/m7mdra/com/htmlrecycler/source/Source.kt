package m7mdra.com.htmlrecycler.source

import org.jsoup.nodes.Document

interface Source {

    fun get(): Document

}