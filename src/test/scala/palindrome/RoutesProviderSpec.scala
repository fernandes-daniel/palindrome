package palindrome

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import palindrome.PalindromeService.IsPalindrome
import spray.json.DefaultJsonProtocol._
import testutil.MockResetBeforeEach

class RoutesProviderSpec extends WordSpec with Matchers with MockResetBeforeEach with ScalatestRouteTest
  with JsonSupport{

  val palindromeService = mockWithReset(classOf[PalindromeService])

  val routesProvider = new RoutesProvider(palindromeService)

  "The service" should {

    "return the list of palindromes on GET of /palindrome?pageNumber=x" in{
      val savedPalindromes = List("kayak", "Anna")

      when(palindromeService.getPalindromes(0)).thenReturn(savedPalindromes)

      Get("/palindrome?pageNumber=0") ~> routesProvider.routes ~> check{
        responseAs[List[String]] shouldBe List("kayak", "Anna")
      }

      verify(palindromeService).getPalindromes(0)
      verifyNoMoreInteractions(palindromeService)
    }

    "return true when posted a valid palindrome" in {
      when(palindromeService.processPalindromeCandidate("kayak")).thenReturn(IsPalindrome(true))

      Post("/palindrome", "kayak") ~> routesProvider.routes ~> check{
        responseAs[IsPalindrome].isPalindrome shouldBe true
      }

      verify(palindromeService).processPalindromeCandidate("kayak")
      verifyNoMoreInteractions(palindromeService)
    }


  }

}
