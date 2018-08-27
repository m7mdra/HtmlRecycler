package m7mdra.com.htmlrecycler.source

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File

class FileSource(val file: File) : Source {
    override fun get(): Document {
        return Jsoup.parse(file, "UTF-8")
    }
}