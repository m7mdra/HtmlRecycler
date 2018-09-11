package m7mdra.com.htmlrecycler.elements

import m7mdra.com.htmlrecycler.model.AnchorLink
import m7mdra.com.htmlrecycler.model.DescriptionList

sealed class Element

data class DescriptionListElement(val descriptionList: List<DescriptionList>) : Element()
data class OrderListElement(val list: Pair<String, List<String>>) : Element()
data class ImageElement(val ImageUrl: String) : Element()
data class ParagraphElement(val paragraph: String) : Element()
data class UnOrderListElement(val list: Pair<String, List<String>>) : Element()
data class Heading1Element(val text: String) : Element()
data class Heading2Element(val text: String) : Element()
data class Heading3Element(val text: String) : Element()
data class Heading4Element(val text: String) : Element()
data class Heading5Element(val text: String) : Element()
data class Heading6Element(val text: String) : Element()
data class VideoElement(val videoSourceUrl: String, val videoThumbnailUrl: String = "") : Element()
data class AudioElement(val audioSourceUrl: String) : Element()
data class AnchorLinkElement(val anchorUrl: AnchorLink) : Element()
data class BlockQuoteElement(val text: String) : Element()
data class IFrameElement(val url: String) : Element()
data class UnknownElement(val html:String) : Element()

sealed class Paragraph

data class Body(val bodyText: String) : Paragraph()
data class AnchorLinkInParagraph(val text: String, val url: String) : Paragraph()
data class UnderLine(val text: String) : Paragraph()
data class Bold(val text: String) : Paragraph()
data class Emphasizes(val text: String) : Paragraph()
class Unknown : Paragraph()
