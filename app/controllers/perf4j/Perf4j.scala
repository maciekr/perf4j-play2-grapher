package controllers.perf4j

import play.api.mvc.Request
import play.api.mvc.Result
import play.api.mvc.AnyContent
import play.api.mvc.Action
import org.perf4j.StopWatch
import org.perf4j.slf4j.Slf4JStopWatch
/**
 * @author maciek_r
 * 
 * simplest perf4j action wrapper that sets off stopwatch
 */
trait Perf4j {
	def ProfiledAction(tag: String)(f: Request[AnyContent] => Result): Action[AnyContent] = {
		Action { request =>
		  val stopWatch = new Slf4JStopWatch()
		  stopWatch.start(tag);
		  try {
			f(request)
		  } finally {
		    stopWatch.stop();
		  }		  
		}
	}	
}