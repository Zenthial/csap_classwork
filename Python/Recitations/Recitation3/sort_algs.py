def part(arr, pivot):
    left, middle, right = [], [], []
    for elm in arr:
        if elm > pivot:
            right.append(elm)
        elif elm < pivot:
            left.append(elm)
        else:
            middle.append(elm)
    return left, middle, right

def quick_sort(arr):
    if len(arr) == 0:
        return []
    else:
        left, middle, right = part(arr, arr[0])
        return quick_sort(left) + middle + quick_sort(right)
    
print(quick_sort([5, 4, 7, 10, 23, 15, 0, 1, 3, 2]))