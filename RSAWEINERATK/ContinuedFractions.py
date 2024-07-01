CFListT = list[int]
CVListT = list[tuple[int, int]]


def rational_to_contfrac(x: int, y: int) -> tuple[CFListT, CVListT]:

    a = x // y
    cflist = [a]
    cvlist = [(a, 1)]
    ppn, ppd = 1, 0
    pn, pd = a, 1
    while a * y != x:
        x, y = y, x - a * y
        a = x // y
        cflist.append(a)
        cn, cd = a * pn + ppn, a * pd + ppd
        cvlist.append((cn, cd))
        ppn, ppd = pn, pd
        pn, pd = cn, cd
    return cflist, cvlist
