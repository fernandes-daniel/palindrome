# Checklist

1. does it run out of the box following the instructions
2. can the tests be run and do they pass
3. are the tests reasonably thorough, do they cover enough edge cases
4. if you add more tests do they pass immediately?
5. how are the tests broken down, unit/functional/acceptance, bdd?
6. does it fulfil all the criteria for the task
7. above and beyond (what extras have been provided)

## Does it run out of the box following the instructions

- (✓) build.sh appeared to do the job installing all pre-requisities 
- (✓) runApi.sh and runUi.sh started the relevant services on their respective ports 
- (-) API Didn't appear to work on localhost (only 127.0.0.1) although I heavily suspect my machine being the problem in this particular case.
- (✕) The API ALWAYS requires pageNumber and pageSize instead of making them optional (i.e. defaulting to typical values)


## Can the tests be run and do they pass

- (✓) Once i'd figured out how to run the tests they all ran fine.
- (✕) The tests don't run when doing sbt test (which could be a massive blocker for a build system). Inability to identify the cause here is a real minus score.
They "can" be run from intellij successfully, however if we didn't have intellij available to us then we wouldn't have been able to validate the tests.
    
## Are the tests reasonably thorough, do they cover enough edge cases

- (✓) Works well for standard characters (Cyrillic and Kanji characters included)
- (✓) Works fine for effectively blank strings (i.e. "!!!!")
- (✕) Missing further non typical characters (emojis)

## If you add more tests do they pass immediately?

- (✓) Test setup was easy enough to add additional tests to and newer tests passed immediately (aside from identified issues) 

## how are the tests broken down, unit/functional/acceptance, bdd?

- (✓) Small set of functional tests to cover the basics 
- (✓) Tests are individual and the textual criteria helps to identify what is being tested.
- (x) Functional tests are heavily mocked so don't really test much other than a method invocation happened.
- (x) Functional tests make some odd assertions `call the palindromes paged filter with the saved palindromes and discard the received date in the result` - no actual testing of discard by date occurs here because that part is mocked out.
- (x) No tests on the node/react app.

## does it fulfil all the criteria for the task


- (✓) uses a POST request for adding the palindrome
- (✓) uses a GET request for getting the list of palindromes
- (✓) returns a json response with `{isPalindrome:true}` and a `200` status code (with `application/json content` type)
- (✓) when provided with excessively long strings doesn't wrap the UI but does give a good response still
- (✓) when provided with an invalid palidrome it responds with a 200 status code and {isPalindrone: false}
- (-) when provided with a content-type that is invalid it doesn't care `curl -vvv -X POST http://127.0.0.1:8080/palindrome -H "Content-Type: text/html"`
- (✕) Have to perform the build.sh every time we need to restart the UI and recreate webpack things (i.e. app bundle)
- (✕) `curl -vvv -X POST http://127.0.0.1:8080/palindrome` with no post data responded with 200 OK `{"isPalindrome":true}`
- (✕) The same palindrome can be added multiple times (they should be unique)
- (✕) when provided with invalid input `curl -vvv -X POST http://127.0.0.1:8080/palindrome -H "Content-Type: application/json" -d '{"yeah":"blah"}'` it returns 200 status code with {isPalindrone:false} - it should probably return a 400 as the input was not a valid type (i.e. it was json when we expected urlencoded form data)
- (✕) when provided with a "potentially valid" input of smiley emoji it fails

## code quality

- (-) The usage of guice is questionable (in play2 it might make more sense as this is the defacto, in akka it's not) 
- (-) The processor seems overly tied to the storage mechanism. 
- (-) Seems over-engineered (processor/service/validator/filter/factory).
- (-) The storage itself is never pruned but the `PagedPalindromeFilter` does filter out results from the set
- (✕) If you insert 101 items and then request the 50th page with a page size of 2 you should get 1 item, i.e. item 101 but instead you get nothing.
- (✕) Is the number `totalPalindromes` in `PalindromePage` supposed to represent the total number of palindromes we have TOTAL in the system now, or just the ones returned in this request? Because you can just check the length of the returns items if you want to know how many items were returned in the request. A more useful piece of information is how many palindromes there are in the system to see.
- (✕) 14.873174 seconds to do 100 iterations when the palindrome list is 1million items big, thus approximating to 400 requests in 1 min (6 req/sec)
- (✕) `PagedPalindromeFilter` could use `.sortBy(-_.received.getMillis)` instead of sortBy AND reverse
- (✕) `PagedPalindromeFilter` could use `.slice` instead of drop AND take
- (✕) No pruning takes place so over time we're hanging onto palindromes that have expired, and correspondingly computing sort and filter over them continuously when they're not required.
- (✕) A typical application expects more reads than writes thus it would make sense to do the filtering on write rather than on each read.
- (✕) `PalindromeProcessor` is potentially thread unsafe due to the `process` method rewriting the `var savedPalindromes` - 2 simultaneous invocations could clobber one another, see `https://stackoverflow.com/questions/46258558/scala-objects-and-thread-safety` for a decent example.


## above and beyond (what extras have been provided)

- none
