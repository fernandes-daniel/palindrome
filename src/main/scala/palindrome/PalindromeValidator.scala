package palindrome

import javax.inject.Singleton

@Singleton
class PalindromeValidator {
  private val WHITE_SPACES_AND_PUNCTUATION_PATTERN = """[\p{Punct}||\s]"""

  def isPalindrome(palindromeCandidate:String) = {
    val standardizedPalindromeCandidate = palindromeCandidate
      .replaceAll(WHITE_SPACES_AND_PUNCTUATION_PATTERN, "")
      .toLowerCase

    standardizedPalindromeCandidate == standardizedPalindromeCandidate.reverse
  }

}
