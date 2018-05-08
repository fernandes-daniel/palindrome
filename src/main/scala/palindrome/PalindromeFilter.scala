package palindrome

import javax.inject.{Inject, Singleton}
import org.joda.time.DateTime

case class PalindromeFilterConf (maxPalindromesPerRequest: Int, maxPalindromeAgeInMinutes: Int)

@Singleton
class PalindromeFilter @Inject()(conf: PalindromeFilterConf) {

  def filter(palindromes: List[Palindrome], page: Int): List[Palindrome] = {
    palindromes
      .filter(m => m.received.isAfter(new DateTime().minusMinutes(conf.maxPalindromeAgeInMinutes)))
      .drop(page * conf.maxPalindromesPerRequest)
      .take(conf.maxPalindromesPerRequest)
  }

}
