package palindrome

import org.joda.time.DateTime
import org.scalatest.{Matchers, WordSpec}

class PagedPalindromeFilterSpec extends WordSpec with Matchers{

  def setupScenario(maxPalindromesPerRequest:Int = 100,
                    maxPalindromeAgeInMinutes:Int = 10) = new {
    val fixture = new PagedPalindromeFilter(PalindromeFilterConf(
      maxPalindromesPerRequest = maxPalindromesPerRequest,
      maxPalindromeAgeInMinutes = maxPalindromeAgeInMinutes
    ))
  }

  "filter" should {

    "filter out the palindromes that are older than a certain amount of minutes" in {
      val scenario = setupScenario()

      val palindrome1 = Palindrome("palindrome1", new DateTime().minusMinutes(8))
      val palindrome2 = Palindrome("palindrome2", new DateTime().minusMinutes(9))
      val palindrome3 = Palindrome("palindrome3", new DateTime().minusMinutes(11))
      val palindrome4 = Palindrome("palindrome4", new DateTime())
      val palindrome5 = Palindrome("palindrome5", new DateTime().minusMinutes(15))

      scenario.fixture.filter(
        List( palindrome1, palindrome2, palindrome3, palindrome4, palindrome5 ),
        0,
        10
      ) shouldBe
        PalindromePage(List( palindrome4, palindrome1, palindrome2 ), 0, 3)
    }

    "only return the most recent configurable amount of palindromes (even if more are requested)" in {
      val scenario = setupScenario(maxPalindromesPerRequest = 3)

      val palindrome1 = Palindrome("palindrome1", new DateTime().minusMinutes(5))
      val palindrome2 = Palindrome("palindrome2", new DateTime().minusMinutes(2))
      val palindrome3 = Palindrome("palindrome3", new DateTime().minusMinutes(5))
      val palindrome4 = Palindrome("palindrome4", new DateTime().minusMinutes(1))
      val palindrome5 = Palindrome("palindrome5", new DateTime().minusMinutes(3))

      scenario.fixture.filter(
        List( palindrome1, palindrome2, palindrome3, palindrome4, palindrome5 ),
        0,
        10
      ) shouldBe
        PalindromePage(List( palindrome4, palindrome2, palindrome5 ), 0, 3)
    }

    "only return the amount of palindromes requested for the page specified" in {
      val scenario = setupScenario()

      val palindrome1 = Palindrome("palindrome1", new DateTime().minusMinutes(1))
      val palindrome2 = Palindrome("palindrome2", new DateTime().minusMinutes(2))
      val palindrome3 = Palindrome("palindrome3", new DateTime().minusMinutes(3))
      val palindrome4 = Palindrome("palindrome4", new DateTime().minusMinutes(4))
      val palindrome5 = Palindrome("palindrome5", new DateTime().minusMinutes(5))

      scenario.fixture.filter(
        List( palindrome1, palindrome2, palindrome3, palindrome4, palindrome5 ),
        1,
        2
      ) shouldBe
        PalindromePage(List( palindrome3, palindrome4 ), 1, 5)
    }




  }

}
