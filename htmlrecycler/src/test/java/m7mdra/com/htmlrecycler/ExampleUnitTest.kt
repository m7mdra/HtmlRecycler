package m7mdra.com.htmlrecycler

import junit.framework.Assert.*
import m7mdra.com.htmlrecycler.elements.*
import m7mdra.com.htmlrecycler.extractor.*
import m7mdra.com.htmlrecycler.model.AnchorLink
import m7mdra.com.htmlrecycler.model.DescriptionList
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.jsoup.select.Elements

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {

    @Test
    fun testIdentifier() {
        val parse = Jsoup.parse(Data.data)
        assertNotNull(parse)
        val elements = parse.body().children()
        val elementList = mutableListOf<m7mdra.com.htmlrecycler.elements.Element>()
        extractData(elementList, elements)

    }

    private fun extractData(elementList: MutableList<m7mdra.com.htmlrecycler.elements.Element>, elements: Elements) {
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
                        val list = mutableListOf<Paragraph>()
                        val dataNodes = it.childNodes()
                        dataNodes.forEach { node ->
                            if (node is TextNode)
                                list.add(Body(node.text()))
                            else if (node is Element)
                                when {
                                    node.tagName() == "a" ->
                                        list.add(AnchorLinkInParagraph(node.text(), node.absUrl("href")))
                                    node.tagName() == "b" || node.tagName() == "strong" ->
                                        list.add(Bold(node.text()))
                                    node.tagName() == "em" || node.tagName() == "i" ->
                                        list.add(Emphasizes(node.text()))
                                    node.tagName() == "u" || node.tagName() == "span" &&
                                            node.attributes()["style"].contains("underline") ->
                                        list.add(UnderLine(node.text()))
                                    else -> list.add(Unknown())
                                }
                        }
                        assertEquals(dataNodes.size, list.size)

                    }
                }
                ElementType.BlockQuote -> {
                    elementList.add(BlockQuoteElement(it.text()))
                }

                ElementType.IFrame -> {

                }
                ElementType.Audio -> {
                    val audio = AudioExtractor(it).extract()
                    elementList.add(AudioElement(audio))
                }
                ElementType.Unknown -> {
                    elementList.add(UnknownElement(it.toString()))
                }

                else -> {
                    elementList.add(UnknownElement(it.toString()))
                }
            }
        }
    }

    object Data {
        val data = "<h1 id=\"firstHeading\" class=\"firstHeading\" lang=\"en\">Hyundai Atos</h1>\n" +
                "<p><img src=\"https://raw.githubusercontent.com/m7mdra/starter/aa67e186d49147a09286f176eb213ec52f6842ca/media/Officials_from_the_South_Sudan_Referendum_Commission_SSRC_use_a_car_-_10.jpg\" alt=\"\" width=\"610\" height=\"397\" /></p>\n" +
                "<p>The <strong>Hyundai   Atos</strong> was a city car produced by the <em>South Korean manufacturer Hyundai</em> since <span  style=\"text-decoration: underline;\">1997</span>. It was also marketed under the <strong>Atoz</strong>, Amica and Santro Xing model names. It was facelifted in 1999, from when it marketed as the Atos Prime, and in 2003." +
                "<a href=\"http://example.com\">anchored text</a> It has been available only with a five-door hatchback body style. It was replaced in most markets by the Hyundai i10 in 2007, but production continued in India till late 2014</p>\n" +
                "<p><img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/6/68/2006_Hyundai_Amica_CDX_1.1_Front.jpg/1024px-2006_Hyundai_Amica_CDX_1.1_Front.jpg\" alt=\"\" width=\"615\" height=\"409\" /></p>\n" +
                "<h1>Overview</h1>\n" +
                "<p>The first <strong>Atos</strong> was introduced in 1997. It is fitted with a 999cc engine and has a top speed of 88 mph (142 km/h). It was succeeded by a face-lift version by 2000 and in 2003 another one. It was discontinued in Europe in 2007, in favour of the Indian assembled i10 and in other markets in 2011.  </p>\n" +
                "<h1>Other names</h1>\n" +
                "<p>The Atos Prime is marketed as the:</p>\n" +
                "<ul>\n" +
                "<li>Hyundai Amica in the United Kingdom</li>\n" +
                "<li>Hyundai Santro Xing in India</li>\n" +
                "<li>Atos by Dodge in Mexico</li>\n" +
                "<li>Kia Visto in Indonesia and South Korea (Hyundai also sold the original Atoz in Indonesia).</li>\n" +
                "<li>Inokom Atos in Malaysia</li>\n" +
                "</ul>\n" +
                "<p>Hyundai entered the Indian market in 1997 with the Atos, marketed under the name Santro and various variants as Zip Drive, Zip Plus etc. Produced at Hyundai's factory in Chennai, its primary competitor at that time was the popular Maruti Suzuki Zen. The distinctive styling of the first generation had mixed reviews, but the car was a success primarily due to its power steering feature and price.</p>\n" +
                "<h3>Variants in India</h3>\n" +
                "<p>The Hyundai Santro was first released in India in 1998.It came with three variants:</p>\n" +
                "<ul>\n" +
                "<li>Santro base, with AC and black bumpers, priced at 2,99,000 INR ex-showroom</li>\n" +
                "<li>Santro DX 1, added with front power windows, central locking, high quality fabric upholstery an fabric inserts on door trims, body colored bumpers, rear wiper/washer/defogger, front fog lamps, waistline moulding and full wheel covers, priced at 3,49,000 INR.</li>\n" +
                "<li>Santro DX 2, added with power steering and first in class rear seat belts, priced at 3,69,000 INR. These were often criticised for their appearance.</li>\n" +
                "<li>In 2000, a new version called Santro Zip Drive was introduced. Changes included were things such as every variant got a power steering and a new grille.</li>\n" +
                "<li>In 2002, a refreshed version called Santro Zip Plus was introduced. Changes included Clear Lens headlamps and taillamps and a better engine and performance.</li>\n" +
                "<li>In 2003, the car underwent a complete facelift and was introduced as the Santro Xing. Pictures are given below alongside technical data.</li>\n" +
                "</ul>\n" +
                "<h3>Specifications</h3>\n" +
                "<p>Figures as listed for the 2000-2003, 5 door, 1.0i model with GSi trim as sold in the UK (as the Amica), with standard options.</p>\n" +
                "<h4>Dimensions</h4>\n" +
                "<ul>\n" +
                "<li>Length: 3,495 mm (137.6 in)</li>\n" +
                "<li>Width: 1,495 mm (58.9 in)</li>\n" +
                "<li>Height: 1,580 mm (62.2 in)</li>\n" +
                "<li>Wheelbase: 2,380 mm (93.7 in)</li>\n" +
                "<li>Unladen weight: 847 kg (1,867 lb)</li>\n" +
                "</ul>\n" +
                "<h4>Technical data</h4>\n" +
                "<ul>\n" +
                "<li>Fuel Delivery: Multi-Point Injection</li>\n" +
                "<li>Transmission: 5-speed manual</li>\n" +
                "<li>Engine size &amp; layout: 996 cc, I4, 12 valves</li>\n" +
                "<li>Peak Power: 40 kW (54.4 PS; 53.6 bhp)</li>\n" +
                "<li>Peak Torque: 82 N&sdot;m (60 lb&sdot;ft)</li>\n" +
                "<li>0-60 mph (96 km/h): 14.6 s</li>\n" +
                "<li>Top Speed: 160 km/h (99 mph)</li>\n" +
                "<li>Fuel economy: 6.4 L/100 km (44 mpg‑imp; 37 mpg‑US)</li>\n" +
                "<li>Emissions: 151 g CO2/km, other emissions below Euro III standard.</li>\n" +
                "</ul>\n" +
                "<p><video controls=\"controls\" width=\"300\" height=\"150\">\n" +
                "<source src=\"https://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_20mb.mp4\" type=\"video/mp4\" /></video></p>\n" +
                "<p><audio src=\"https://freemusicarchive.org/file/music/Creative_Commons/Dead_Combo/CC_Affiliates_Mixtape_1/Dead_Combo_-_01_-_Povo_Que_Cas_Descalo.mp3\" controls=\"controls\"></audio></p>"
    }


//    class TableElement(val table: Table) : Element()


//    data class Table(val heads: MutableList<String> = mutableListOf(), val rows: MutableList<Row> = mutableListOf())


    /*
    *
     <table>
        <thead>
            <tr>
                <th>Product</th>
                <th>Cost</th>
                <th>Really?</th>
            </tr>
       </thead>
       <tbody>
            <tr>
                <td>TinyMCE</td>
                <td>Free</td>
                <td>YES!</td>
           </tr>
           <tr>
                <td>Plupload</td>
                <td>Free</td>
                <td>YES!</td>
                </tr>
        </tbody>
     </table>
    * */
/*    class TableExtractor(val e: org.jsoup.nodes.Element) : Extractor<Table>(e) {
        override fun extract(): Table {
            val table = Table()
            e.select("tr").forEach { tr ->
                val tableHead = tr.select("th thead")
                val tableData = tr.select("td")

                if (tableHead.size > 0)
                    tableHead.forEach {
                        table.heads.add(it.text())
                    }
                else
                    tableData.map { it.text() }.apply {
                        table.rows.add(Row(this))
                    }
            }
            return table
        }
    }*/
}

sealed class Paragraph

data class Body(val bodyText: String) : Paragraph()
data class AnchorLinkInParagraph(val text: String, val url: String) : Paragraph()
data class UnderLine(val text: String) : Paragraph()
data class Bold(val text: String) : Paragraph()
data class Emphasizes(val text: String) : Paragraph()
class Unknown : Paragraph()
