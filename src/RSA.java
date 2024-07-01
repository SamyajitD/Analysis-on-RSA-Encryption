// package Source;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class RSA {
    private long key1, key2;
    private long n;

    private long binpow(long a, long b, long mod) {
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

    private long gcd(long a, long b, long[] xy) {
        if (a == 0) {
            xy[0] = 0;
            xy[1] = 1;
            return b;
        }
        long[] x1y1 = new long[2];
        long res = gcd(b % a, a, x1y1);
        xy[0] = x1y1[1] - (b / a) * x1y1[0];
        xy[1] = x1y1[0];
        return res;
    }

    public void setKeys() {
        long prime1 = 67;
        long prime2 = 79;

        n = prime1 * prime2;
        long phi = (prime1 - 1) * (prime2 - 1);
        long e = 2;

        while (true) {
            if (gcd(e, phi, new long[2]) == 1)
                break;
            e++;
        }
        System.out.println("e : " + e);
        key1 = e;
        long d;
        d = 2;

        while (true) {
            if ((d * e) % phi == 1)
                break;
            d++;
        }
        System.out.println("d: " + d);

        key2 = d;
    }

    public long encrypt(long message) {
        return binpow(message, key1, n);
    }

    public long decrypt(long encryptedText) {
        return binpow(encryptedText, key2, n);
    }

    public List<Long> encoder(String message) {
        List<Long> msg = new ArrayList<>();
        for (char letter : message.toCharArray())
            msg.add(encrypt((long) letter));
        return msg;
    }

    public String decoder(List<Long> encoded) {
        StringBuilder s = new StringBuilder();
        for (long num : encoded)
            s.append((char) decrypt(num));
        return s.toString();
    }

    public static void main(String[] args) {
        RSA rsa = new RSA();
        rsa.setKeys();
        String s = "Hi There";
        List<Long> v = rsa.encoder(s);
        System.out.println("Encrypted Message : ");
        v.forEach((elem) -> System.out.print(elem + " "));
        System.out.println("\nDecrypted Message : ");
        String sh = rsa.decoder(v);
        System.out.println(sh);
    }
}
