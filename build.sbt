name := "hello"

version := "1.0"

scalaVersion := "2.10.3"

resolvers += Resolver.mavenLocal

// use unmanaged dependencies
// mkdir lib; cp $(find ../test1/fop-trunk/ -iname "*.jar") lib/

////http://oklampy.wordpress.com/2013/05/30/working-around-bad-dependency-declarations/
//libraryDependencies ++= Seq(
//  "org.apache.avalon.framework" % "avalon-framework-api" % "4.2.0" from "http://repo1.maven.org/maven2/avalon-framework/avalon-framework-api/4.2.0/avalon-framework-api-4.2.0.jar",
//  "org.apache.avalon.framework" % "avalon-framework-impl" % "4.2.0" from "http://repo1.maven.org/maven2/avalon-framework/avalon-framework-impl/4.2.0/avalon-framework-impl-4.2.0.jar",
////  "org.apache.xmlgraphics" % "xmlgraphics-commons" % "1.5",  // from "http://repo1.maven.org/maven2/org/apache/xmlgraphics/xmlgraphics-commons/1.5/xmlgraphics-commons-1.5.jar",
////  "org.apache.xmlgraphics" % "fop" % "1.1")
//  "me.portabella.david.org.apache.xmlgraphics" % "fop" % "1.1_trunk_20140404_175230")

// mvn install:install-file -Dfile=/Users/david/Dropbox/health/celiac_cards/test1/fop-trunk/build/fop.jar -Dpackaging=jar -DgroupId=me.portabella.david.org.apache.xmlgraphics -DartifactId=fop -Dversion=1.1_trunk_20140404_175230


// http://stackoverflow.com/questions/12801477/class-not-found-fop


//libraryDependencies += "org.apache.xmlgraphics" % "fop" % "1.0"
