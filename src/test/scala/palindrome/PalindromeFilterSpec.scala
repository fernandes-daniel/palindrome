package palindrome

import org.joda.time.DateTime
import org.scalatest.{Matchers, WordSpec}

class PalindromeFilterSpec extends WordSpec with Matchers{

  "filter" should {

    "filter out the palindromes that are older than a certain amount of minutes" in {
      val testScenario = buildTestScenario(
        maxPalindromeAgeInMinutes = 10
      )

      val palindrome1 = Palindrome("palindrome1", new DateTime().minusMinutes(8))
      val palindrome2 = Palindrome("palindrome2", new DateTime().minusMinutes(9))
      val palindrome3 = Palindrome("palindrome3", new DateTime().minusMinutes(11))
      val palindrome4 = Palindrome("palindrome4", new DateTime())
      val palindrome5 = Palindrome("palindrome5", new DateTime().minusMinutes(15))

      testScenario.fixture.filter(
        List( palindrome1, palindrome2, palindrome3, palindrome4, palindrome5 ),
        0
      ) shouldBe
        List( palindrome1, palindrome2, palindrome4 )
    }

    "only return the palindromes for the page specified" in {
      val testScenario = buildTestScenario(
        maxPalindromesPerRequest = 2
      )

      val palindrome1 = Palindrome("palindrome1", new DateTime())
      val palindrome2 = Palindrome("palindrome2", new DateTime())
      val palindrome3 = Palindrome("palindrome3", new DateTime())
      val palindrome4 = Palindrome("palindrome4", new DateTime())
      val palindrome5 = Palindrome("palindrome5", new DateTime())

      testScenario.fixture.filter(
        List( palindrome1, palindrome2, palindrome3, palindrome4, palindrome5 ),
        1
      ) shouldBe
        List( palindrome3, palindrome4 )
    }

  }


  def buildTestScenario(maxPalindromesPerRequest:Int = 100,
                        maxPalindromeAgeInMinutes:Int = 10) = new {
    val fixture = new PalindromeFilter(PalindromeFilterConf(
      maxPalindromesPerRequest,
      maxPalindromeAgeInMinutes
    ))
  }

}
