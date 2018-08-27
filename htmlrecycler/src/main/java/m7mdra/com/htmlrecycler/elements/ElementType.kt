package m7mdra.com.htmlrecycler.elements

enum class ElementType(type: Int) {
    Unknown(-1),
    Heading1(1),
    Heading2(2),
    Heading3(3),
    Heading4(4),
    Heading5(5),
    Heading6(6),

    IFrame(7),

    BlockQuote(8),

    Paragraph(9),

    AnchorLink(10),

    Image(11),
    Video(12),
    Audio(13),

    OrderedList(14),
    DescriptionList(15),
    Div(16),

    Table(17),
    UnorderedList(18)


}
