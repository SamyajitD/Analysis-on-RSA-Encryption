from Crypto.PublicKey import RSA
from Crypto.Util.number import (
    long_to_bytes,
    bytes_to_long,
    GCD
)
from base64 import b64decode

import argparse
import sys


def invert(a, n):
    g, x, _ = egcd(a, n)
    if g != 1:
        raise Exception('Modular inverse does not exist')
    else:
        return x % n


def iroot(n, k):
    u, s = n, n+1
    while u < s:
        s = u
        t = (k-1) * s + n // pow(s, k-1)
        u = t // k
    return s


def neg_pow(a, b, n):
    assert b < 0
    assert GCD(a, n) == 1
    res = invert(a, n)
    res = pow(res, b*(-1), n)
    return res


def common_modulus(e1, e2, n, c1, c2):
    g, a, b = egcd(e1, e2)
    if a < 0:
        c1 = neg_pow(c1, a, n)
    else:
        c1 = pow(c1, a, n)
    if b < 0:
        c2 = neg_pow(c2, b, n)
    else:
        c2 = pow(c2, b, n)
    ct = c1*c2 % n
    m = iroot(ct, g)
    return m


def parse_args():
    parser = argparse.ArgumentParser(description="A simple script to perform RSA common modulus attacks.",
                                     epilog="More info at https://github.com/HexPandaa/RSA-Common-Modulus-Attack/")
    parser.add_argument("-c1", type=argparse.FileType("r"), metavar="ciphertext1", required=True,
                        help="The first ciphered message")
    parser.add_argument("-c2", type=argparse.FileType("r"), metavar="ciphertext2", required=True,
                        help="The second ciphered message")
    parser.add_argument("-k1", type=argparse.FileType("rb"), metavar="pubkey1", required=True,
                        help="The first public key")
    parser.add_argument("-k2", type=argparse.FileType("rb"), metavar="pubkey2", required=True,
                        help="The second public key")
    parser.add_argument("-o", type=argparse.FileType("wb"), metavar="outfile", required=False,
                        help="Output file")
    args = parser.parse_args()
    return args


def bytes_to_integer(data):
    output = 0
    size = len(data)
    for index in range(size):
        output |= data[index] << (8 * (size - 1 - index))
    return output


def integer_to_bytes(integer, _bytes):
    output = bytearray()
    for byte in range(_bytes):
        output.append((integer >> (8 * (_bytes - 1 - byte))) & 255)
    return output


def egcd(a, b):
    if (a == 0):
        return (b, 0, 1)
    else:
        g, y, x = egcd(b % a, a)
        return (g, x - (b // a) * y, y)






def main(args):
    pubkey1 = RSA.import_key(args.k1.read())
    pubkey2 = RSA.import_key(args.k2.read())
    c1 = b64decode(args.c1.read())
    c1 = bytes_to_long(c1)
    c2 = b64decode(args.c2.read())
    c2 = bytes_to_long(c2)

    if pubkey1.n != pubkey2.n:
        sys.stderr.write(
            "[ERROR] The modulus of both public keys must be the same\n")
        sys.exit(1)
    if GCD(pubkey1.e, pubkey2.e) != 1:
        sys.stderr.write(
            "[ERROR] The greatest common denominator between the exponent of each keys should be 1\n")
        sys.exit(2)
    deciphered_message = common_modulus(
        pubkey1.e,
        pubkey2.e,
        pubkey1.n,
        c1,
        c2
    )
    deciphered_bytes = long_to_bytes(deciphered_message)

    print("[+] Recovered message:")
    print(deciphered_message)
    print("[+] Recovered bytes:")
    print(deciphered_bytes)

    if args.o:
        args.o.write(deciphered_bytes)


if __name__ == '__main__':
    args = parse_args()
    main(args)
