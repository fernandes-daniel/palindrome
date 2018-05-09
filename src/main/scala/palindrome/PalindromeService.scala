package palindrome

import javax.inject.{Inject, Singleton}
import palindrome.PalindromeService.{GetPalindromePageResult, ProcessPalindromeCandidateResult}

@Singleton
class PalindromeService @Inject()(palindromeProcessor: PalindromeProcessor,
                                  pagedPalindromeFilter: PagedPalindromeFilter) {
  def processPalindromeCandidate(palindromeCandidate: String): ProcessPalindromeCandidateResult = {
    ProcessPalindromeCandidateResult(
      palindromeProcessor.process(palindromeCandidate)
    )
  }

  def getPalindromePage(pageNumber: Int, pageSize: Int): GetPalindromePageResult = {
    val palindromePage = pagedPalindromeFilter
      .filter(palindromeProcessor.getSavedPalindromes, pageNumber, pageSize)

    GetPalindromePageResult(
      palindromePage.palindromes.map(_.data),
      palindromePage.totalPalindromes
    )
  }

}

object PalindromeService {
  case class ProcessPalindromeCandidateResult(isPalindrome: Boolean)

  case class GetPalindromePageResult(palindromes:List[String], totalPalindromes:Int)
}
