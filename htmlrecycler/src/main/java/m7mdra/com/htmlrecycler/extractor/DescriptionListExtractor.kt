package m7mdra.com.htmlrecycler.extractor

import org.jsoup.nodes.Element

class DescriptionListExtractor(val element: Element) : Extractor<List<Pair<String, String>>>(element) {
    override fun extract(): List<Pair<String, String>> {
        val list = mutableListOf<Pair<String, String>>()
        val dts = element.getElementsByTag("dt")
        val dds = element.getElementsByTag("dd")
        dts.forEachIndexed { index, element ->
            list.add(Pair(element.text(), dds[index].text()))
        }
        return list
    }
}