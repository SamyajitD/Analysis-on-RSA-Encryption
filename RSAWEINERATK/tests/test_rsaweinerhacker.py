import pytest
from RSAwienerHacker import hack_RSA
from tests.fixtures.rsa_data import TEST_RSA_HACK


@pytest.mark.parametrize("e,n,c,m", TEST_RSA_HACK)
def test_hack_RSA_preconfigured(e: int, n: int, c: int, m: int):
    d = hack_RSA(e, n)
    decrypted_m = pow(c, d, n)
    assert decrypted_m == m


def test_hack_RSA_autogenerated(gen_e: int, gen_n: int, gen_d: int):
    hacked_d = hack_RSA(gen_e, gen_n)
    assert gen_d == hacked_d
