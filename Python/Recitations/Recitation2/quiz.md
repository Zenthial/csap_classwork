# Quiz
1) There is no base case. blast_off needs to check if the countdown == 0 and then stop calling itself
2) The elif needs to return n * fact(n-1), else fact(n-1) where n != 1 or 0 never returns, and gives the none error
3) C
4) .
```py
def gcd(m, n):
    if m % n == 0:
        return n
    else:
        return gcd(n, m % n)
```
5) gcd(12, 90) -> gcd(90, 12) -> gcd(12, 6) -> 6