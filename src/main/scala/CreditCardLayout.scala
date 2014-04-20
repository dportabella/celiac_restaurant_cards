import java.io.File
import scala.xml._

object CreditCardLayout {
  def main(args: Array[String]) = {

    buildPdf("english")
  }


  val outputDir = new File("target/credit_card_layout")

  def buildPdf(language: String) {
    outputDir.mkdirs()
    val foDoc = buildFo(language)
    println(foDoc)
    FOPHelper.buildPdf(foDoc, new File(outputDir, language + ".pdf"))
  }


  val boldAttribute = Attribute(None, "font-weight", Text("bold"), Null)
  val redAttribute = Attribute(None, "color", Text("red"), Null)

  case class Parameters(font: String = "Arial", strongAttribute: Attribute = boldAttribute, titleSize: String, size: String, titleSpace: String, space: String, thanksSpace: String, lastSpace: String)

  val parametersMap = Map(
    "english" -> Parameters(font="Arial",      strongAttribute=boldAttribute, titleSize="9pt", size="7.5pt", titleSpace="0.20cm", space="0.20cm", thanksSpace="0.38cm", lastSpace="0.1cm"),
    "catalan" -> Parameters(font="Arial",      strongAttribute=boldAttribute, titleSize="9pt", size="7.5pt", titleSpace="0.20cm", space="0.20cm", thanksSpace="0.38cm", lastSpace="0.1cm"),
    "turkish"  -> Parameters(font="Arial",     strongAttribute=boldAttribute, titleSize="9pt", size="7.0pt", titleSpace="0.20cm", space="0.15cm", thanksSpace="0.38cm", lastSpace="0.1cm"),
    "japanese" -> Parameters(font="MS Mincho", strongAttribute=redAttribute,  titleSize="8pt", size="6.7pt", titleSpace="0.20cm", space="0.20cm", thanksSpace="0.38cm", lastSpace="0.1cm")
  )

  def buildFo(language: String): xml.Node = {
    val input = scala.xml.XML.load(getClass.getResourceAsStream(language + ".xml"))
    val parameters = parametersMap(language)
    buildFo(input, parameters, language)
  }

  def buildFo(input: xml.Node, parameters: Parameters, language: String): xml.Node = {
    val title = language(0).toUpper + language.substring(1)

    def convertParagraph(paragraph: String) = convertStrong((input \ paragraph)(0).child, parameters.strongAttribute)

    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:fox="http://xmlgraphics.apache.org/fop/extensions">
      <fo:layout-master-set>
        <fo:simple-page-master master-name="simple"
                               page-height="5.4cm" page-width="8.5cm"
                               margin-top="0pt" margin-bottom="0pt" margin-left="2pt" margin-right="2pt">
          <fo:region-body margin-top="0cm"/>
          <fo:region-before extent="0cm"/>
          <fo:region-after extent="0cm"/>
        </fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence master-reference="simple">
        <fo:flow flow-name="xsl-region-body">
          <fo:block border-width="2pt" border-style="solid" border-color="#829660" fox:border-radius="10pt">
            { <fo:block font-size={ parameters.titleSize } font-family="Arial" font-weight="bold" text-align="center" space-after={ parameters.titleSpace }>
                { title } Gluten Free Restaurant Card
              </fo:block>
            }
            { <fo:block font-size={ parameters.size } font-family={ parameters.font } text-align="center" space-after={ parameters.space }>
                { convertParagraph("p1") }
              </fo:block>
            }
            { <fo:block font-size={ parameters.size } font-family={ parameters.font } text-align="center" space-after={ parameters.space }>
                { convertParagraph("p2") }
              </fo:block> % parameters.strongAttribute
            }
            { <fo:block font-size={ parameters.size } font-family={ parameters.font } text-align="center" space-after={ parameters.space }>
                { convertParagraph("p3") }
              </fo:block>
            }
            { <fo:block font-size={ parameters.size } font-family={ parameters.font } text-align="center" space-after={ parameters.thanksSpace }>
                { convertParagraph("p4") }
              </fo:block>
            }
            { <fo:block font-size={ parameters.size } font-family={ parameters.font } text-align="center" space-after={ parameters.lastSpace }>
                { convertParagraph("p5") }
              </fo:block> % parameters.strongAttribute
            }
            { <fo:block font-size={ parameters.size } font-family={ parameters.font } text-align="center" space-after={ parameters.lastSpace }>
                © Copyright celiactravel.com
              </fo:block>
            }
          </fo:block>
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  }

  def convertStrong(nodes: Seq[Node], strongAttribute: Attribute): Seq[Node] = {
    def updateStrong_(node: Node): Node = node match {
      case <strong>{ ch @ _* }</strong> => <fo:inline>{ ch }</fo:inline> % strongAttribute
      case other @ _ => other
    }
    nodes.map(updateStrong_)
  }

}
