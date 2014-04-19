import java.io.{StringReader, File, FileOutputStream, BufferedOutputStream}
import javax.xml.transform.sax.SAXResult
import javax.xml.transform.stream.StreamSource
import javax.xml.transform.TransformerFactory
import org.apache.fop.apps.FopFactory

import scala.xml.Node

object FOPUtils {
//  val fopFactory = FopFactory.newInstance(new File(".").toURI())
  val fopFactory = FopFactory.newInstance()
  val factory = TransformerFactory.newInstance()
  val transformer = factory.newTransformer()

  def buildPdf(node: Node) {
    println("Ha!")
    val out = new BufferedOutputStream(new FileOutputStream(new File("/tmp/file.pdf")))
    try {
      val fop = fopFactory.newFop("application/pdf", out)   // TODO: for some reason, it does compile with MimeConstants.MIME_PDF

      val src = new StreamSource(new StringReader(node.toString()))
//      val src = new StreamSource(new File("english.fo"))
      val res = new SAXResult(fop.getDefaultHandler())
      transformer.transform(src, res)
    } finally {
      out.close()
    }
    println("done!")
  }

}

/*
https://xmlgraphics.apache.org/fop/1.1/embedding.html
http://www.codeproject.com/Articles/37663/PDF-Generation-using-XSLFO-and-FOP

 */

//
//import scala.xml.Node
//
//object FOPUtils {
//  def buildPdf(node: Node) {
//    println("Ha!")
//  }
//
//}
