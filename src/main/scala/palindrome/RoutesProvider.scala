package palindrome

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import javax.inject.{Inject, Singleton}
import palindrome.Server.{as, complete, entity, get, parameters, pathEnd, pathPrefix, post}
import spray.json.DefaultJsonProtocol._

@Singleton
class RoutesProvider @Inject()(palindromeService: PalindromeService) extends JsonSupport {

  def routes: Route =
    pathPrefix("palindrome") {
      pathEnd {
        post {
          entity(as[String]) {
            palindrome => {
              complete {
                palindromeService.processPalindromeCandidate(palindrome)
              }
            }
          }
        } ~
          get {
            parameters(
              'pageNumber.as[Int]
            ) {
              pageNumber => {
                complete(palindromeService.getPalindromes(pageNumber))
              }
            }
          }
      }
    }

}
