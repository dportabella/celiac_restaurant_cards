import java.io.{File, FileOutputStream, BufferedOutputStream}
import javax.xml.transform.sax.SAXResult
import javax.xml.transform.stream.StreamSource
import javax.xml.transform.TransformerFactory
import org.apache.fop.apps.FopFactory
import org.apache.fop.apps.Fop
import org.apache.fop.apps.MimeConstants

import scala.xml.Node

object FOPUtils {
  val fopFactory = FopFactory.newInstance(new File(".").toURI())
  val factory = TransformerFactory.newInstance()
  val transformer = factory.newTransformer()

  def buildPdf(node: Node) {
    println("Ha!")
    val out = new BufferedOutputStream(new FileOutputStream(new File("/tmp/file.pdf")))
    try {
      val fop = fopFactory.newFop(MimeConstants.MIME_PDF, out)
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

//
//import scala.xml.Node
//
//object FOPUtils {
//  def buildPdf(node: Node) {
//    println("Ha!")
//  }
//
//}
