name := """kutten-met-scala-yay"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  filters,
  "org.scalatestplus.play" %% "scalatestplus-play"    % "2.0.0" % Test,
  "io.spray"               %% "spray-httpx"           % "1.3.3",
  "mysql"                   % "mysql-connector-java"  % "5.1.36",
  "com.typesafe.play"      %% "play-slick"            % "2.1.0",
  "com.typesafe.play"      %% "play-slick-evolutions" % "2.1.0"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
