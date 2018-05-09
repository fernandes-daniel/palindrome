package palindrome

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import palindrome.PalindromeService.{GetPalindromePageResult, ProcessPalindromeCandidateResult}
import testutil.MockResetBeforeEach

class RoutesProviderSpec extends WordSpec with Matchers with MockResetBeforeEach with ScalatestRouteTest
  with JsonSupport{

  val palindromeService = mockWithReset(classOf[PalindromeService])

  val routesProvider = new RoutesProvider(palindromeService)

  "The service" should {

    "return the list of palindromes on GET of /palindrome?pageNumber=x" in{
      val getPalindromePageResult = GetPalindromePageResult(List("kayak", "Anna"),10)

      when(palindromeService.getPalindromePage(0, 2)).thenReturn(getPalindromePageResult)

      Get("/palindrome?pageNumber=0&pageSize=2") ~> routesProvider.routes ~> check{
        responseAs[GetPalindromePageResult] shouldBe getPalindromePageResult
      }

      verify(palindromeService).getPalindromePage(0, 2)
      verifyNoMoreInteractions(palindromeService)
    }

    "return true when posted a valid palindrome" in {
      when(palindromeService.processPalindromeCandidate("kayak")).thenReturn(ProcessPalindromeCandidateResult(true))

      Post("/palindrome", "kayak") ~> routesProvider.routes ~> check{
        responseAs[ProcessPalindromeCandidateResult].isPalindrome shouldBe true
      }

      verify(palindromeService).processPalindromeCandidate("kayak")
      verifyNoMoreInteractions(palindromeService)
    }


  }

}
