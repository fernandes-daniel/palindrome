package palindrome

import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import palindrome.PalindromeService.IsPalindrome
import testutil.MockResetBeforeEach
import testutil.builders.PalindromeBuilder

class PalindromeServiceSpec extends WordSpec with Matchers with MockResetBeforeEach {

  val palindromeProcessor = mockWithReset(classOf[PalindromeProcessor])
  val palindromeFilter = mockWithReset(classOf[PalindromeFilter])

  val palindromeService = new PalindromeService(palindromeProcessor, palindromeFilter)

  "processPalindromeCandidate" should {

    "call the palindrome processor to process the palindrome and wrap the result in a case class" in {

      when(palindromeProcessor.process("kayak")).thenReturn(true)

      palindromeService.processPalindromeCandidate("kayak") shouldBe IsPalindrome(true)

    }
  }

  "getPalindromes" should {

    "call the palindromes filter with the saved palindromes and discard the received date in the result" in {
      val savedPalindromes = List(PalindromeBuilder("kayak"),PalindromeBuilder("anna"))

      when(palindromeProcessor.getSavedPalindromes).thenReturn(savedPalindromes)

      when(palindromeFilter.filter(savedPalindromes, 1)).thenReturn( List(PalindromeBuilder("anna")) )

      palindromeService.getPalindromes(1) shouldBe List("anna")
    }
  }
}
