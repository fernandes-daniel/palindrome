package palindrome

import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import testutil.MockResetBeforeEach

class PalindromeProcessorSpec extends WordSpec with Matchers with MockResetBeforeEach {

  val palindromeValidator = mockWithReset(classOf[PalindromeValidator])
  val palindromeFactory   = mockWithReset(classOf[PalindromeFactory])

  val palindromeProcessor = new PalindromeProcessor(palindromeValidator, palindromeFactory)

  "process" should {

    "check if a palindrome is valid and save it if it is" in {
      val palindromeString = "kayak"
      val palindrome       = mock(classOf[Palindrome])

      when(palindromeValidator.isPalindrome(palindromeString)).thenReturn(true)
      when(palindromeFactory.newPalindrome(palindromeString)).thenReturn(palindrome)

      palindromeProcessor.process(palindromeString) shouldBe true

      palindromeProcessor.getSavedPalindromes shouldBe List(palindrome)

      verify(palindromeValidator).isPalindrome(palindromeString)
      verifyNoMoreInteractions(palindromeValidator)

      verify(palindromeFactory).newPalindrome(palindromeString)
      verifyNoMoreInteractions(palindromeFactory)

    }

    "check if a palindrome is valid and discard if it is not" in {
      val palindromeString = "invalid"

      when(palindromeValidator.isPalindrome(palindromeString)).thenReturn(false)

      palindromeProcessor.process(palindromeString) shouldBe false

      palindromeProcessor.getSavedPalindromes shouldBe List.empty

      verify(palindromeValidator).isPalindrome(palindromeString)
      verifyNoMoreInteractions(palindromeValidator)

      verifyZeroInteractions(palindromeFactory)
    }

    /**
      * struggles significantly here.
      */
    "add the same palindrome lots of times and we should only have 1 palindrome" in {
      import org.mockito.Mockito._
//      def ti

      (0 until 10000).foreach { i =>
        val s = Array.fill(10)("a").mkString
        when(palindromeValidator.isPalindrome(s)).thenReturn(true)
        palindromeProcessor.process(s) shouldBe true
      }

      palindromeProcessor.getSavedPalindromes.length shouldBe 1
    }


  }

  override protected def beforeEach(): Unit = {
    palindromeProcessor.resetSavedPalindromes
    super.beforeEach()
  }
}
