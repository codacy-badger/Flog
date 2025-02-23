organization := "com.phasmidsoftware"

name := "flog"

version := "1.0.2"

scalaVersion := "2.13.5"

scalacOptions += "-deprecation"

val scalaTestVersion = "3.1.1"
val logBackVersion = "1.2.3"

scalacOptions in (Compile,doc) ++= Seq("-groups", "-implicits", "-deprecation")

resolvers += "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
  "ch.qos.logback" % "logback-classic" % logBackVersion % "runtime"
)
