#1a
s = 'staple'
stale = s[0:3] + s[4:]
print(stale)

#1b
result = ''
for c in s:
    if c != 'p':
        result += c
print(result)

#1c
result = ''
for i in range(len(s)):
    c = s[i]
    if c != 'p':
        result += c
print(result)

#2
print(s[len(s)-1], s[-1])

#3
s = 'sparkle'
print(s[::2])

#4
# input is a string, not an int, so it will error checking a string against an int

#5
# fib never returns, in the if statement, you need to return n

def fib(n):
    if n <= 1:
        return n
    else:
        return fib(n-1) + fib(n-2)
    
print('fib(5)', fib(5))

r = [1, 2, 3]
print(r[-1])