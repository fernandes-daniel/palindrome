package palindrome

import org.joda.time.DateTime
import org.scalatest.{Matchers, WordSpec}

class PagedPalindromeFilterSpec extends WordSpec with Matchers {

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

    /**
      * Total palindromes should be the number of valid palindromes (that haven't expired)
      * in the system, instead we get a capped counter because the request will only ever
      * return 100 at max (the default). The max returned items in the request differs from the
      * total number of items in the system.
      */
    "should give me the correct number of elements in the system (total)" in {
      val scenario = setupScenario()
      val l = (0 until 1000).map { i=>
        Palindrome(s"palindrome$i", new DateTime())
      }.toList

      scenario.fixture.filter(
        l,
        1,
        2
      ).totalPalindromes shouldBe
        1000
    }

    "should give me page 51 with an element" in {
      val scenario = setupScenario()
      val l = (0 until 101).map { i=>
        Palindrome(s"palindrome$i", new DateTime())
      }.toList

      scenario.fixture.filter(
        l,
        50,
        2
      ).palindromes.length should be(1)
    }

    /**
      * Performance is going to suffer here because we're scanning the whole list of items
      * every time we want to filter out 1 element.
      */
    "should respond in a good amount of time" in {

      val scenario = setupScenario()
      val l = (0 until 1000000).map { i=>
        Palindrome(s"palindrome$i", new DateTime())
      }.toList

      val f = () => {
        (0 until 100).foreach { _ =>
          scenario.fixture.filter(
            l,
            1,
            1
          ).palindromes.length should be(1)
        }

      }
      val takenInSeconds = time(f)().toFloat/1000000000
      println(s"Time taken $takenInSeconds seconds")
      takenInSeconds should be < 1.0F
    }

    def time[T](f: () => T): () => Long = {
      val start = System.nanoTime()
      f()
      () => System.nanoTime()-start
    }


  }

}
