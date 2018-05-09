package palindrome

import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import palindrome.PalindromeService.{GetPalindromePageResult, ProcessPalindromeCandidateResult}
import testutil.MockResetBeforeEach
import testutil.builders.{PalindromeBuilder, PalindromePageBuilder}

class PalindromeServiceSpec extends WordSpec with Matchers with MockResetBeforeEach {

  val palindromeProcessor = mockWithReset(classOf[PalindromeProcessor])
  val palindromeFilter = mockWithReset(classOf[PagedPalindromeFilter])

  val palindromeService = new PalindromeService(palindromeProcessor, palindromeFilter)

  "processPalindromeCandidate" should {

    "call the palindrome processor to process the palindrome and wrap the result in a case class" in {

      when(palindromeProcessor.process("kayak")).thenReturn(true)

      palindromeService.processPalindromeCandidate("kayak") shouldBe ProcessPalindromeCandidateResult(true)

    }
  }

  "getPalindromePage" should {

    "call the palindromes paged filter with the saved palindromes and discard the received date in the result" in {
      val savedPalindromes = List(PalindromeBuilder("kayak"),PalindromeBuilder("anna"))

      when(palindromeProcessor.getSavedPalindromes).thenReturn(savedPalindromes)

      when(palindromeFilter.filter(savedPalindromes, 1, 3)).thenReturn(
        PalindromePageBuilder(palindromes = List(PalindromeBuilder("anna")), totalPalindromes = 10)
      )

      palindromeService.getPalindromePage(1, 3) shouldBe GetPalindromePageResult(List("anna"), 10)
    }
  }
}
