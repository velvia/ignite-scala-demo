val mySettings = Seq(organization := "org.velvia",
                     scalaVersion := "2.10.4",
                     libraryDependencies ++= deps) ++
                 universalSettings

mySettings

lazy val deps = Seq(
  "org.apache.ignite" % "ignite-core" % "1.4.0",
  "org.apache.ignite" % "ignite-indexing" % "1.4.0")

//////////////////////////
///

lazy val coreSettings = Seq(
  scalacOptions ++= Seq("-Xlint", "-deprecation", "-Xfatal-warnings", "-feature"),
  // needed to disable Unsafe warning
  javacOptions ++= Seq("-XDignore.symbol.file")
)

lazy val universalSettings = coreSettings ++ styleSettings

lazy val compileScalastyle = taskKey[Unit]("compileScalastyle")

lazy val styleSettings = Seq(
  scalastyleFailOnError := true,
  compileScalastyle := org.scalastyle.sbt.ScalastylePlugin.scalastyle.in(Compile).toTask("").value,
  // Is running this on compile too much?
  // (compile in Compile) <<= (compile in Compile) dependsOn compileScalastyle
  (Keys.`package` in Compile) <<= (Keys.`package` in Compile) dependsOn compileScalastyle
)
