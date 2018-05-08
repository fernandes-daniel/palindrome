package palindrome

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import palindrome.PalindromeService.IsPalindrome
import spray.json.DefaultJsonProtocol._

trait JsonSupport extends SprayJsonSupport{
  implicit val isPalindromeFormat = jsonFormat1(IsPalindrome)
}
