package palindrome

import javax.inject.{Inject, Singleton}

@Singleton
class PalindromeProcessor @Inject()(palindromeValidator: PalindromeValidator,
                                    palindromeFactory: PalindromeFactory) {

  private var savedPalindromes = List.empty[Palindrome]

  def process(palindromeCandidate: String): Boolean = {
    palindromeValidator isPalindrome palindromeCandidate match {
      case true => {
        savedPalindromes = savedPalindromes :+ palindromeFactory.newPalindrome(palindromeCandidate)
        true
      }
      case false => false
    }
  }

  def getSavedPalindromes = savedPalindromes

  def resetSavedPalindromes = savedPalindromes = List.empty
}