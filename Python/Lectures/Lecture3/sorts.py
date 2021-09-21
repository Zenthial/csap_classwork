"""
CSAPX Week 3: Searching and Sorting

Author: Sean Strout @ RIT CS

Here lies the remains of the hallowed sorting algorithms:

1. selection_sort (in place):
    best=O(n^2) comparisons/swaps
    worst=O(n^2): comparisons/swaps
2. insertion_sort (in place):
    best=O(n) comparisons and O(1) swaps,
    worst=O(n^2) comparisons/swaps
3. merge_sort (not in place):
    best=O(nlogn)
    worst=O(nlogn)
4. quick_sort (not in place):
    best=O(nlogn)
    worst=O(n^2)

To run:
    $ python3 rit_sort.py
"""

# the leading underscore(_) is a python convention for private function

def _find_min_index(data: list[int], mark: int) -> int:
    """
    A helper routine for selection_sort which finds the index
    of the smallest value in data at the mark index or greater
    :param data: The data to be sorted (a list)
    :param mark: An index which is in range 0..len(data)-1 inclusive
    :return: An index which is in range 0..len(data)-1 inclusive
    """

    # assume the minimum value is at initial mark position
    min_index = mark
    # loop over the remaining positions greater than the mark
    for mark in range(mark+1, len(data)):
        # if a smaller value is found, record its index
        if data[mark] < data[min_index]:
            min_index = mark
    return min_index

def selection_sort(data: list[int]) -> None:
    """
    Perform an in-place selection sort of data
    :param data: The data to be sorted (a list)
    """
    for mark in range(len(data)-1):
        min_index = _find_min_index(data, mark)
        # swap the element at marker with the min index
        data[mark], data[min_index] = data[min_index], data[mark]

def insertion_sort(data: list[int]) -> None:
    """
    Perform an in-place insertion sort of data
    :param data: The data to be sorted (a list)
    """
    for mark in range(1, len(data)):
        j = mark
        while j > 0 and data[j-1] > data[j]:
            data[j], data[j-1] = data[j-1], data[j]
            j -= 1

def _split(data: list[int]) -> tuple[list[int], list[int]]:
    """
    Split the data into halves and return the two halves
    :param data: The list to split in half
    :return: Two lists, cut in half
    """
    return data[:len(data)//2], data[len(data)//2:]

def _alt_split(data: list[int]) -> tuple[list[int], list[int]]:
    """
    For CS nerds who may not have indexing, but for some reason have append?
    """
    first = True
    one = []
    two = []
    for x in data:
        if first:
            one.append(x)
        else:
            two.append(x)
        first = not first
    return one,two

def _merge(left: list[int], right: list[int]) -> list[int]:
    """
    Merges two sorted list, left and right, into a combined sorted result
    :param left: A sorted list
    :param right: A sorted list
    :return: One combined sorted list
    """
    # the sorted left + right will be stored in result
    result = []
    left_index, right_index = 0, 0

    # loop through until either the left or right list is exhausted
    while left_index < len(left) and right_index < len(right):
        if left[left_index] <= right[right_index]:
            result.append(left[left_index])
            left_index += 1
        else:
            result.append(right[right_index])
            right_index += 1

    # take the un-exhausted list and extend the remainder onto the result
    if left_index < len(left):
        result.extend(left[left_index:])
    elif right_index < len(right):
        result.extend(right[right_index:])

    return result

def merge_sort(data: list[int]) -> list[int]:
    """
    Performs a merge sort and returns a newly sorted list
    :param data: The data to be sorted (a list)
    :return: A sorted list
    """
    # an empty list, or list of 1 element is already sorted
    if len(data) < 2:
        return data
    else:
        # split the data into left and right halves
        left, right = _split(data)
        # return the merged recursive merge_sort of the halves
        return _merge(merge_sort(left), merge_sort(right))

def _partition(data: list[int], pivot: int) \
      -> tuple[list[int], list[int], list[int]]:
    """
    Three way partition the data into smaller, equal and greater lists,
    in relationship to the pivot
    :param data: The data to be sorted (a list)
    :param pivot: The value to partition the data on
    :return: Three list: smaller, equal and greater
    """
    less, equal, greater = [], [], []
    for element in data:
        if element < pivot:
            less.append(element)
        elif element > pivot:
            greater.append(element)
        else:
            equal.append(element)
    return less, equal, greater

def quick_sort(data: list[int]) -> list[int]:
    """
    Performs a quick sort and returns a newly sorted list
    :param data: The data to be sorted (a list)
    :return: A sorted list
    """
    if len(data) == 0:
        return []
    else:
        pivot = data[0]
        less, equal, greater = _partition(data, pivot)
        return quick_sort(less) + equal + quick_sort(greater)

def test_sorts() -> None:
    """
    Test function for the sorts
    """
    SORTS = (selection_sort, insertion_sort, merge_sort, quick_sort)
    DATA = (
            (),
            (0,),   # oh tuples, you subtle beast!
            (0,1),
            (1,0),
            (0,1,2),
            (0,2,1),
            (1,0,2),
            (1,2,0),
            (2,0,1),
            (2,1,0),
            (9,5,2,6,3,8,1,4,0,7)  # <- lol, me being SOOO random, xDDDD!!11
    )

    for sort_fn in SORTS:
        for data in DATA:
            sort_data, sorted = list(data), list(data)
            if sort_fn == selection_sort or sort_fn == insertion_sort:
                sort_fn(sort_data)              # in place
            else:
                sort_data = sort_fn(sort_data)  # not in place
            sorted.sort()
            print(sort_fn.__name__ + ':', list(data), 'result:', sort_data,
                  'OK' if sort_data == sorted else 'FAIL!')
        print()

if __name__ == '__main__':
    test_sorts()