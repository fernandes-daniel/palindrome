package palindrome

import javax.inject.{Inject, Singleton}
import palindrome.PalindromeService.IsPalindrome

@Singleton
class PalindromeService @Inject()(palindromeProcessor: PalindromeProcessor,
                                  palindromeFilter: PalindromeFilter) {
  def processPalindromeCandidate(palindromeCandidate: String): IsPalindrome = {
    IsPalindrome(
      palindromeProcessor.process(palindromeCandidate)
    )
  }


  def getPalindromes(pageNumber: Int): List[String] = {
    palindromeFilter
      .filter(palindromeProcessor.getSavedPalindromes, pageNumber)
      .map(_.data)
  }

}

object PalindromeService {
  case class IsPalindrome(isPalindrome: Boolean)
}
