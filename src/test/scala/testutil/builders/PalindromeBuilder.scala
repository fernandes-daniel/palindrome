package testutil.builders

import org.joda.time.DateTime
import palindrome.Palindrome

object PalindromeBuilder {

  def apply(data:String = "data",
            received: DateTime = new DateTime()): Palindrome =
    Palindrome(
      data,
      received
    )
}
