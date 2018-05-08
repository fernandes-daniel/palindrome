package palindrome

import org.joda.time.DateTime

case class Palindrome(data:String, received: DateTime)

class PalindromeFactory{
  def newPalindrome(palindrome:String) = {
    Palindrome(palindrome, new DateTime())
  }
}
