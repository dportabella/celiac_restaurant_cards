object Hi {
  def main(args: Array[String]) = {
    println("Hi!")
//    println(scala.io.Source.fromFile("src/resources/english.fo").getLines.mkString("\n"))
    println(scala.io.Source.fromInputStream(getClass.getResourceAsStream("english.fo")).getLines.mkString("\n"))
  }
}
