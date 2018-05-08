package palindrome

import org.scalatest.{Matchers, WordSpec}

class PalindromeValidatorSpec extends WordSpec with Matchers {

  val palindromeValidator = new PalindromeValidator

  "isPalindrome" should {

    "return true" when {

      "given palindromes" in {

        val validPalindromes = List(
          "Dammit I'm Mad",
          "Kayak",
          "A nut for a jar of tuna.",
          "Are we not drawn onward to new era?",
          "Maps, DNA, and spam."
        )


        validPalindromes.foreach(
          palindrome => palindromeValidator.isPalindrome(palindrome) shouldBe true
        )

      }
    }

    "return false" when {


      "given non palindromes" in {
        val nonPalindromes = List(
          "Something somewhere",
          "The sky is blue",
          "Tom"
        )


        nonPalindromes.foreach(
          palindrome => palindromeValidator.isPalindrome(palindrome) shouldBe false
        )
      }

    }

  }
}
