name := "hello"

version := "1.0"

scalaVersion := "2.10.3"

resolvers += Resolver.mavenLocal

// for the moment, let's use unmanaged dependencies
// mkdir lib; cp $(find ../test1/fop-trunk/ -iname "*.jar") lib/

// libraryDependencies += "org.apache.xmlgraphics" % "fop" % "1.0"
// libraryDependencies += "org.apache.xmlgraphics" % "fop" % "1.1"

// to use a release from trunk (which contains the rounded-corners feature),
// i should create a pom.xml file based on here, and update the dependencies
// http://repo1.maven.org/maven2/org/apache/xmlgraphics/fop/1.1/fop-1.1.pom
// mvn install:install-file -Dfile=./build/fop.jar -DpomFile=./fop-1.1_trunk_20140404_175230.xml
// libraryDependencies += "me.portabella.david.org.apache.xmlgraphics" % "fop" % "1.1_trunk_20140404_175230b")
