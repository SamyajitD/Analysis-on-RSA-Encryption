import RSAvulnerableKeyGenerator


def pytest_generate_tests(metafunc):
    if 'gen_e' in metafunc.fixturenames:
        # Autogenerate 5 test cases for
        # test_rsaweinerhacker.test_hack_RSA_autogenerated
        metafunc.parametrize(
            "gen_e,gen_n,gen_d",
            [RSAvulnerableKeyGenerator.generateKeys(1024) for _ in range(5)],
        )
