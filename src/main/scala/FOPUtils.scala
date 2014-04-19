import java.io.{File, FileOutputStream, BufferedOutputStream}
import javax.xml.transform.dom.DOMSource
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
      val fop = fopFactory.newFop("application/pdf", out)   // TODO: for some reason, it does compile with MimeConstants.MIME_PDF

      val doc = new ElemExtras(node.asInstanceOf[xml.Elem]).toJdkDoc
      val src = new DOMSource(doc);
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


//From: http://snipplr.com/view/55854/convert-scala-xml-to-java-dom/
import scala.xml.{Text, Elem, Node, Comment, Atom}
implicit def nodeExtras(n: Node) = new NodeExtras(n)
implicit def elemExtras(e: Elem) = new ElemExtras(e)

object XmlHelpers {
  val docBuilder =
    javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder()
}

class NodeExtras(n: Node) {
  def toJdkNode(doc: org.w3c.dom.Document): org.w3c.dom.Node =
    n match {
      case Elem(prefix, label, attributes, scope, children @ _*) =>
        // XXX: ns
        val r = doc.createElement(label)
        for (a <- attributes) {
          r.setAttribute(a.key, a.value.text)
        }
        for (c <- children) {
          r.appendChild(c.toJdkNode(doc))
        }
        r
      case Text(text) => doc.createTextNode(text)
      case Comment(comment) => doc.createComment(comment)
      // not sure
      case a: Atom[_] => doc.createTextNode(a.data.toString)
      // XXX: other types
      //case x => throw new Exception(x.getClass.getName)
    }
}

class ElemExtras(e: Elem) extends NodeExtras(e) {
  override def toJdkNode(doc: org.w3c.dom.Document) =
    super.toJdkNode(doc).asInstanceOf[org.w3c.dom.Element]

  def toJdkDoc = {
    val doc = XmlHelpers.docBuilder.newDocument()
    doc.appendChild(toJdkNode(doc))
    doc
  }
}

