// package Source;

import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;

public class BigTest {
    public static BigInteger generatePrime() {
        SecureRandom random = new SecureRandom();
        int bitSize = 2000;
        while (true) {
            BigInteger randomBigInteger = new BigInteger(bitSize, random);
            if (randomBigInteger.isProbablePrime(100)) {
                return randomBigInteger;
            }

        }

    }

    public static void main(String[] args) {
        System.out.println("Prime Generated : " + generatePrime().toString(0));
    }
}
