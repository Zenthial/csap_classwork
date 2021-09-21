def shift(c, k):
    mx = ord("Z")
    mn = ord("A")
    uni = ord(c)
    while uni + k > mx:
        k = (uni + k) - mx
    return chr(uni+k)

print(shift("Z", 26))