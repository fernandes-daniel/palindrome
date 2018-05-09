package palindrome

import javax.inject.{Inject, Singleton}
import org.joda.time.DateTime

case class PalindromeFilterConf (maxPalindromesPerRequest: Int, maxPalindromeAgeInMinutes: Int)

case class PalindromePage(palindromes:List[Palindrome], pageNumber:Int, totalPalindromes:Int)

@Singleton
class PagedPalindromeFilter @Inject()(conf: PalindromeFilterConf) {

  def filter(palindromes: List[Palindrome], page: Int, pageSize: Int): PalindromePage = {
    val allLatestPalindromes = palindromes
      .filter(m => m.received.isAfter(new DateTime().minusMinutes(conf.maxPalindromeAgeInMinutes)))
      .sortBy(_.received.getMillis)
      .reverse
      .take(conf.maxPalindromesPerRequest)

    val pagePalindromes = allLatestPalindromes
      .drop(page * pageSize)
      .take(pageSize)

    PalindromePage(pagePalindromes, page, allLatestPalindromes.length)
  }

}
