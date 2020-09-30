name := "Perun"

version := "0.0.1"

scalaVersion := "2.13.3"

lazy val root = project in file(".")

val zioVersion = "1.0.1"
libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value % Provided,

  "com.propensive"  %% "magnolia"       % "0.16.0",

  "org.typelevel" %% "cats-core"       % "2.2.0",

  "dev.zio"         %% "zio-test"       % zioVersion % Test,
  "dev.zio"         %% "zio-test-sbt"   % zioVersion % Test,
  "dev.zio"         %% "zio-test-junit" % zioVersion % Test,
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8"
)

testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
