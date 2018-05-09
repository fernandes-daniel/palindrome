package testutil.builders

import palindrome.{Palindrome, PalindromePage}

object PalindromePageBuilder {

  def apply(palindromes:List[Palindrome] = List(PalindromeBuilder(),PalindromeBuilder()),
            pageNumber:Int = 0,
            totalPalindromes:Int = 10): PalindromePage =
    PalindromePage(
      palindromes,
      pageNumber,
      totalPalindromes
    )
}
