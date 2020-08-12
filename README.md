# Prime number generator

This program will take a range of numbers as input from the command line and generate all primes within that range (inclusive - max value: 2147483647, min value: -2147483648)
Results are output to the console.

### Prerequisites

* [Java](https://www.java.com/) - Version 8 or higher
* [Maven](https://maven.apache.org/) - Build tool


### Installing and running

To build the project with maven:

```
mvn verify
```

Executing requires range of numbers as a command line argument. To run project with Maven (replacing the parameters):

```
mvn exec:java -Dexec.mainClass="com.mattcramblett.primenumbergenerator.Main" -Dexec.args="0 100"
```

## Running the tests

To run unit tests:

```
mvn test
```

## Details

##### Approach
Using test-driven development led me to my initial implementation which was a trial-division algorithm.
Then I improved the performance by only iterating over possible divisors of `n` until `Ceiling(√n)`, since any further would be redundant.
This algorithm runs fairly fast for any *single* number in the possible range of integer values (0 up to `Integer.MAX_VALUE`).
However, I found it to be slow to use when generating prime numbers in a very wide range, because it has to check all potential divisors for every number in the range, as described above. In doing so, it iterates over the same elements repeatedly.
So for the `generate` method, I implemented a modified version of [Sieve of Eratosthenes](https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes). This algorithm uses the principle that a composite number can always be derived by taking the product of a prime number and another number.
So from this you can do a process of elimination by starting at 2 and creating products until `n` is reached, then starting back a the next prime, 3, and so on until all possible composites have been eliminated.
The modification to this algorithm comes in when segmenting the range of numbers, in order to reduce the memory footprint of the original algorithm. This method scales much better for wide ranges of numbers compared to the trial division method.

The disadvantage to this algorithm is that it always starts at 2 to create products. So if there is one number in the range, say 2.1 billion, then it goes from 2 until 2.1 billion in order to eliminate composites all the way up to the upper bound. So it would be no different than asking for a range of 0 to 2.1 billion. This is why I chose to leave `isPrime` as the trial division algorithm, since it scales fine for determining if a single number is prime. The `generate` method uses the segmented sieve algorithm because it is much more effective at ranges of numbers.

##### Important Notes
* When running the program on the command line with Maven for an extremely wide range, it is likely the Java heap space will not be enough, so it might be necessary to increase memory for the JVM. This can be changed in `pom.xml` file with the `-Xmx` argument. 
* There is a test case in `PrimeNumberGeneratorTest` called `testGenerateMaxPrimes`. It generates up to the max possible value, but it takes 24 seconds on my local machine. I want to include it for completeness since it is an edge case, but it is rather slow for a unit test so it is currently ignored. It passes.


