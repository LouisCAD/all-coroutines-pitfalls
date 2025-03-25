# Revamp

## Observations

1. At MRB Lausanne, it took me 11 minutes before I started to dive into the pitfalls subject.
2. At 1h04, It went 24min beyond the planned duration.

## What I want out of this presentation :

1. Fit within 30 to 35 minutes of speaking
2. Find a positive "line" for the presentation
3. "Follow this to avoid mistakes": Provide a simple set of rules to avoid the most common mistakes:
4. - Must have a number of steps that's easy to remember
5. Show how we are tackling those at Infomaniak. Examples:
    - `Result.cancellable()`, and why this choice over `runCancellableCatching` for example.
    - Removing success/failure callbacks from a suspend function
    - Wrapping ALL blocking I/O into `Dispatchers.IO`, [like this](https://github.com/Infomaniak/android-SwissTransfer/pull/451/commits/c96d9805ca32fedb21e0b4e106d0e7a6fb41db92)
    - Replacing not so magic number with a properly done flow (`delay(1000)` with `downloadStatusFlow`)
6. Cover the most common mistakes
7. Have a disclaimer about non-exhaustivity: One can often invent a better way of doing things. One can **always** invent a worst way of doing things.
8. Still mention the uncovered kinds of pitfalls, in a photo-friendly slide outlining them, but don't talk about it, just give people 20 seconds to take a picture.

## Types of issues

- Unbridged callbacks (under-use)
- Performance issues
  - Unwrapped blocking I/O
  - CPU-intensive stuff blocking a thread for too long
  - Non-blocking I/O on Dispatchers.IO
  - Using `runBlocking`
- Unstructured concurrency

**Pitfalls and mistakes are 2 different things!**

X things:
- Actual pitfalls brought to you by kotlinx.coroutines
  - `CancellationException` that has to be rethrown
  - `async` and `await` aren't like in other languages
  - Misuse of `CoroutineScope`
    - Passing a `CoroutineContext` that might have a `Job` inside
      - Use launch + join instead
  - garbage collectable coroutines
  - throwing flows that then get shared (never do this)
  - surprising error propagation because of structured concurrency
    - Can't catch errors around launch or async 
    - Probably a bad idea to catch around `Job.join()` or `Deferred.await()` 
    - Let it be your friend!
  - `runBlocking` works, and will freeze your app
- Pitfalls from Kotlin's stdlib (kotlin.coroutines)
  - suspendCoroutine doesn't support cancellation
  - Messing with `CoroutineContext`
- Pitfalls in external APIs that can be fixed with coroutines
  - APIs doing blocking I/O
  - CPU-intensive operations
  - Callback hell based APIs
- Plain mistakes
  - Improper bridging
    - Not handling cancellation properly
    - Not reporting Throwables properly 
  - Related to the programming model
    - Emitting mutable objects in a Flow

## TODO

- Create illustrations in Keynote, and add them here
- Add the "It's a Trap" GIF (play it without loop, at least once in the presentation)
