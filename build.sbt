val scala3Version = "3.5.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "kyo-playground",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "io.getkyo" %% "kyo-prelude"      % "0.12.2",
      "io.getkyo" %% "kyo-core"         % "0.12.2",
    )

//      libraryDependencies += "io.getkyo" %% "kyo-core"          % "0.12"
//libraryDependencies += "io.getkyo" %% "kyo-direct"        % "0.12"
  )
