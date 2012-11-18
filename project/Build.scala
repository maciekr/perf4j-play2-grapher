import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "perf4j-play-graphing-controller"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.perf4j" % "perf4j" % "0.9.16"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(      
    )

}
