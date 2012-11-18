package controllers.perf4j

import play.api.mvc.{RequestHeader, Action, Controller}
import org.perf4j.chart.StatisticsChartGenerator
import collection.mutable
import org.perf4j.logback.GraphingStatisticsAppender

/**
 * @author maciek_r
 *
 */
object GraphingController extends Controller with Perf4j {
  
  def graph = ProfiledAction("GET.graph") {
    implicit request =>
      val chartsByName = getChartGeneratorsToDisplay(request);
      Ok(views.html.perf4j.graphs(60, chartsByName.foldLeft(List[(String, String)]()) { 
        case (res, ((key, Some(chartGenerator)))) => (key, chartGenerator.getChartUrl) :: res
      })).as("text/html;charset=utf-8")
  }

  private def getChartGeneratorsToDisplay(implicit request: RequestHeader): mutable.LinkedHashMap[String, Option[StatisticsChartGenerator]] = {
    val graphsToDisplay = request.queryString.get("graphName") match {
      case Some(graphNames) => graphNames
      case None => getAllKnownGraphNames
    }
    graphsToDisplay.foldLeft(new mutable.LinkedHashMap[String, Option[StatisticsChartGenerator]]){ (res, graphName) =>
      res += (graphName -> getGraphByName(graphName))
    }
  }

  private def getGraphByName(name: String): Option[StatisticsChartGenerator] = {
    val appender = GraphingStatisticsAppender.getAppenderByName(name);
    if (appender == null)
      None
    else
      Some(appender.getChartGenerator())
  }

  def getAllKnownGraphNames: List[String] = {
    var retVal = List[String]()
    val graphsIter = GraphingStatisticsAppender.getAllGraphingStatisticsAppenders.iterator
    while (graphsIter.hasNext) {
      retVal ::= graphsIter.next().getName
    }
    retVal
  }


}
