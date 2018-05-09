package palindrome

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import palindrome.PalindromeService.{GetPalindromePageResult, ProcessPalindromeCandidateResult}
import spray.json.DefaultJsonProtocol._

trait JsonSupport extends SprayJsonSupport{
  implicit val processPalindromeCandidateResultFormat = jsonFormat1(ProcessPalindromeCandidateResult)
  implicit val getPalindromePageResultFormat = jsonFormat2(GetPalindromePageResult)
}
