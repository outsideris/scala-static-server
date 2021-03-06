name := "httpd"

version := "0.1.0"

scalaVersion := "2.9.2"

scalacOptions ++= Seq("-deprecation")

libraryDependencies += "junit" % "junit" % "4.9" % "test"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.8" % "test"

libraryDependencies += "org.scalaj" %% "scalaj-time" % "0.6"

EclipseKeys.withSource := true
