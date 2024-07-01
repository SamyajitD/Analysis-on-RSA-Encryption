
// !Java program Miller-Rabin
import java.io.*;
import java.math.*;
import java.util.Random;
import java.security.SecureRandom;

public class Rabin {

	private static long binpow(long a, long b, long mod) {
		a %= mod;
		long res = 1;
		while (b > 0) {
			if ((b & 1) == 1)
				res = res * a % mod;
			a = a * a % mod;
			b >>= 1;
		}
		return res;
	}

	static boolean millerTest(long d, long n) {

		long a = 2 + (long) (Math.random() % (n - 4));

		long x = binpow(a, d, n);

		if (x == 1 || x == n - 1)
			return true;

		while (d != n - 1) {
			x = (x * x) % n;
			d *= 2;

			if (x == 1)
				return false;
			if (x == n - 1)
				return true;
		}

		return false;
	}

	static boolean isPrime(long n, long k) {

		if (n <= 1 || n == 4)
			return false;
		if (n <= 3)
			return true;

		long d = n - 1;

		while (d % 2 == 0)
			d /= 2;

		for (long i = 0; i < k; i++)
			if (!millerTest(d, n))
				return false;

		return true;
	}

	public static long generatePrime(long bits) {
		SecureRandom random = new SecureRandom();
		while (true) {
			long x = random.nextLong() % (binpow(2, bits, Long.MAX_VALUE) - binpow(2, bits - 1, Long.MAX_VALUE))
					+ binpow(2, bits - 1, Long.MAX_VALUE);
			if ((x & 1) == 0) {
				continue;
			}
			if (isPrime(x, 10)) {
				return x;
			}
		}
	}

	public static void main(String args[]) {

		System.out.println("Genrerated Prime : " +
				generatePrime(50));

	}
}
