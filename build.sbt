ThisBuild / scalaVersion := "2.12.8"

/* Macro settings */
// addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M11" cross CrossVersion.full)
// scalacOptions += "-Xplugin-require:macroparadise"
// scalacOptions in(Compile, console) ~= (_ filterNot (_ contains "paradise"))
/* Macro settings */

resolvers += Resolver.bintrayRepo("underscoreio", "training")

lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"
// lazy val doodle = "underscoreio" %% "doodle" % "0.8.3"
lazy val scrimageCore = "com.sksamuel.scrimage" %% "scrimage-core" % "2.1.8"
lazy val scrimageExtra = "com.sksamuel.scrimage" %% "scrimage-io-extra" % "2.1.8"
lazy val scrimageFilters = "com.sksamuel.scrimage" %% "scrimage-filters" % "2.1.8"
lazy val betterFiles = "com.github.pathikrit" %% "better-files" % "3.8.0"
lazy val scalaMeta = "org.scalameta" %% "scalameta" % "1.8.0"
lazy val scalaReflect = "org.scala-lang" % "scala-reflect" % "2.12.8"
lazy val scalaSwing = "org.scala-lang.modules" %% "scala-swing" % "2.1.1"

lazy val root = (project in file("."))
  .settings(
    name := "computer-science",
    libraryDependencies ++= Seq(
      scalaTest
      
    )
  )
  .dependsOn(lib)

lazy val lib = (project in file("lib"))
  .settings(
    name := "lib",
    libraryDependencies ++= Seq(
      scalaMeta,
      scalaReflect,
      scrimageCore,
      scrimageExtra,
      scrimageFilters,
      betterFiles,
      scalaSwing
    )
  )

//initialCommands in console :=
//  """
//    |import doodle.core._
//    |import doodle.core.Image._
//    |import doodle.syntax._
//    |import doodle.jvm.Java2DFrame._
//    |import doodle.backend.StandardInterpreter._
//  """.trim.stripMargin
//
//cleanupCommands in console :=
//  """
//    |doodle.jvm.quit()
//  """.trim.stripMargin
