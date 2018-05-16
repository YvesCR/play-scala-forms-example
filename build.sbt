name := """play-scala-forms-example"""

version := "2.6.x"

scalaVersion := "2.12.3"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.1" % "test"
libraryDependencies ++= Seq(
    "org.webjars" % "bootstrap" % "3.3.7"
)

resolvers += "Apache Maven Central Repository" at "http://repo.maven.apache.org/maven2/"

libraryDependencies += "org.fluentd" %% "fluent-logger-scala" % "0.7.0"
