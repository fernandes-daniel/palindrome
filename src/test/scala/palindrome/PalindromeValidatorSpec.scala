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

    "validate the following" when {
      "input is non standard punctuation" in {

        val nonPalindromes = List(
          "`121`", // front and back non typical character
          "1`21", // mid placed non typical character
          "```", // effectively blank input
          "1\\b1" // adding in a backspace
        )

        nonPalindromes.foreach(
          palindrome => palindromeValidator.isPalindrome(palindrome) shouldBe true
        )

      }

      /**
        * @todo Fix this test, it fails emojis
        */
      "input is an emoji" in {
        val palindromes = List(
          "1😀1", // "11"
          "😀" // ""
        )

        palindromes.foreach(
          palindrome => palindromeValidator.isPalindrome(palindrome) shouldBe true
        )
      }

      "input is Cyrillic or Kanji" in {

        val palindromes = List(
          "Ж", // cyrillic
          "ЯЯ", // cyrillic
          "十進数進十", // kanji
          "言語言" // kanji
          //          "هذا هو الملعون جيد رجل الصباح" // right to left arabic
        )

        palindromes.foreach(
          palindrome => palindromeValidator.isPalindrome(palindrome) shouldBe true
        )
      }

      "the string is really long" in {
        val palindromes = List(
          Array.fill[String](1000000)("a").mkString
        )

        palindromes.foreach(
          palindrome => palindromeValidator.isPalindrome(palindrome) shouldBe true
        )
      }
    }


  }
}
