def juggle(msg):
    length = len(msg)
    mid = length // 2 -1
    new_msg: list[str] = []
    for i in range(length-1, -1, -1):
        new_pos = mid-i # if mid >= i else i - mid;
        print(i, mid, new_pos)
        new_msg.insert(new_pos, msg[i])
    
    return ''.join(new_msg)
