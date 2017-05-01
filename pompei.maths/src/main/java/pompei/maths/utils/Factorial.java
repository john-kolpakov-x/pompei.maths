package pompei.maths.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Factorial {

  // main function
  public static BigInteger factorial(int n) {
    return factorial(n, primes(n));
  }

  // recursive function with shared primes array
  private static BigInteger factorial(int n, int[] primes) {
    if (n < 2) return BigInteger.ONE;
    BigInteger f = factorial(n / 2, primes);
    BigInteger ps = primeSwing(n, primes);
    return f.multiply(f).multiply(ps);
  }

  // swinging factorial function
  private static BigInteger primeSwing(int n, int[] primes) {
    List<BigInteger> multipliers = new ArrayList<>();
    for (int i = 0; i < primes.length && primes[i] <= n; i++) {
      int prime = primes[i];
      BigInteger bigPrime = BigInteger.valueOf(prime);
      BigInteger p = BigInteger.ONE;
      int q = n;
      while (q != 0) {
        q = q / prime;
        if (q % 2 == 1) {
          p = p.multiply(bigPrime);
        }
      }
      if (!p.equals(BigInteger.ONE)) {
        multipliers.add(p);
      }
    }
    return product(multipliers, 0, multipliers.size() - 1);
  }

  // fast product for the list of numbers
  private static BigInteger product(List<BigInteger> multipliers, int i, int j) {
    if (i > j) return BigInteger.ONE;
    if (i == j) return multipliers.get(i);
    int k = (i + j) >>> 1;
    return product(multipliers, i, k).multiply(product(multipliers, k + 1, j));
  }

  // Eratosthenes sieve
  private static int[] primes(int upTo) {
    upTo++;
    if (upTo >= 0 && upTo < 3) {
      return new int[]{};
    }

    int length = upTo >>> 1;
    boolean sieve_bool[] = new boolean[length];
    for (int i = 1, iterations = (int) Math.sqrt(length - 1); i < iterations; i++) {
      if (!sieve_bool[i]) {
        for (int step = 2 * i + 1, j = i * (step + 1); j < length; j += step) {
          sieve_bool[j] = true;
        }
      }
    }

    int not_primes = 0;
    for (boolean not_prime : sieve_bool) {
      if (not_prime) not_primes++;
    }

    int sieve_int[] = new int[length - not_primes];
    sieve_int[0] = 2;
    for (int i = 1, j = 1; i < length; i++) {
      if (!sieve_bool[i]) {
        sieve_int[j++] = 2 * i + 1;
      }
    }

    return sieve_int;
  }

}
