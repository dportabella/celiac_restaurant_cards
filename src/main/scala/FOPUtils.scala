import java.io.{File, FileOutputStream, BufferedOutputStream}
import javax.xml.transform.sax.SAXResult
import javax.xml.transform.stream.StreamSource
import javax.xml.transform.TransformerFactory
import org.apache.fop.apps.FopFactory
import org.apache.fop.apps.Fop
import org.apache.fop.apps.MimeConstants

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
//      val fop = fopFactory.newFop(MimeConstants.MIME_PDF, out)
      val fop = fopFactory.newFop("application/pdf", out)
//      val fop = fopFactory.newFop(MimeConstants.MIME_FOP_PRINT, out)
      // val src = new StreamSource(node);
      val src = new StreamSource(new File("english.fo"))
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
