# Prime number generator

This program will take a range of numbers as input from the command line and generate all primes within that range (inclusive - max value: (2^31)-1, min value: -(2^31))
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

