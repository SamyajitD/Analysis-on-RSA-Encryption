import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class breakRSA {
    static long n = 0;

    static long binpow(long a, long b, long mod) {
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

    static long decrypt(long encrpyted_text, long key2, long n) {
        long d = key2;
        long decrypted = binpow(encrpyted_text, d, n);
        return decrypted;
    }

    static String decoder(ArrayList<Long> encoded, long d, long n) {
        StringBuilder s = new StringBuilder();
        for (long num : encoded)
            s.append((char) decrypt(num, d, n));
        return s.toString();
    }

    static boolean checkPrime(long x) {
        for (long i = 2; i * i <= x; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    static ArrayList<Long> generatePrimes(long n) {
        ArrayList<Long> nos = new ArrayList<>();
        boolean[] is_prime = new boolean[(int) (n + 1)];
        Arrays.fill(is_prime, true);
        is_prime[0] = is_prime[1] = false;
        for (long p = 2; p * p <= n; p++) {
            if (n % p == 0) {
                if (is_prime[(int) p]) {

                    if (checkPrime(p)) {
                        if (checkPrime(n / p)) {
                            ArrayList<Long> res = new ArrayList<>();
                            res.add(p);
                            res.add(n / p);
                            return res;
                        }
                    }
                    for (long i = p * p; i <= n; i += p) {
                        is_prime[(int) i] = false;
                    }
                }
            } else {
                is_prime[(int) p] = false;
            }
        }
        return nos;
    }

    static void breakEncryption(long n, long e, ArrayList<Long> cipher, String txt) {
        ArrayList<Long> pr = generatePrimes(n);
        long p = pr.get(0);
        long q = pr.get(1);
        System.out.println(p + " " + q);
        if (p == n || q == n) {
            System.out.println("Error");
            return;
        }
        long phi = (p - 1) * (q - 1);
        long k = 1;
        long d;
        String s;
        while (true) {

            d = (1 + (k * phi)) / e;
            System.out.println("d : " + d);
            s = decoder(cipher, d, n);
            if (s.equals(txt)) {
                System.out.println(d + " " + k);
                System.out.println("Text : " + s);
                break;
            }
            k++;
        }
    }

    // static void breakEncryption(BigInteger n, BigInteger e, ArrayList<BigInteger> cipher, String txt) {
    //     // ArrayList<BigInteger> pr = generatePrimes(n);
    //     // long p = pr.get(0);
    //     // long q = pr.get(1);
    //     // for(BigInteger i=new BigInteger("2");)
    //     BigInteger p = new BigInteger("2");
    //     while (p.compareTo(n) <= 0) {
    //         if (n.remainder(p).equals(BigInteger.ZERO)) {
    //             break;
    //             // System.out.println("Prime divisor found: " + divisor);
    //             // number = number.divide(divisor);
    //         } else {
    //             p = p.add(BigInteger.ONE);
    //         }
    //     }
    //     BigInteger q = new BigInteger("1");
    //     p = n.divide(p);
    //     System.out.println(p + " " + q);
    //     // if (p == n || q == n) {
    //     // System.out.println("Error");
    //     // return;
    //     // }
    //     BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    //     BigInteger k = new BigInteger("1");
    //     BigInteger d;
    //     String s;
    //     BigInteger nm;
    //     while (true) {

    //         nm = BigInteger.ONE.add(k.multiply(phi));
    //         d = nm.divide(e);
    //         System.out.println("d : " + d);
    //         s = decoder(cipher, d, n);
    //         if (s.equals(txt)) {
    //             System.out.println(d + " " + k);
    //             System.out.println("Text : " + s);
    //             break;
    //         }
    //         k++;
    //     }
    // }

    public static void main(String[] args) {
        System.out.println("Start!");
        ArrayList<Long> cipherTexts = new ArrayList<>(
                Arrays.asList(3259L, 566L, 2105L, 3678L, 2173L, 2121L, 858L, 2121L));
        breakEncryption(5293L, 5L, cipherTexts, "Hi There");
        System.out.println("End!");
    }
}
