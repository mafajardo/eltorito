import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "hackathon"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.codehaus.jackson" % "jackson-core-asl" % "1.9.5",
      "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.5",
      "org.json" % "json" % "20090211",
      "commons-httpclient" % "commons-httpclient" % "3.1",
      "org.apache.httpcomponents" % "httpclient" % "4.2.3",
      "org.apache.commons" % "commons-lang3" % "3.1"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here
    )

}
