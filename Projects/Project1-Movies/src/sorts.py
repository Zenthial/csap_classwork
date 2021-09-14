from classes import Rating, Movie

def _part_rating_class(arr: list, pivot: Rating, prop: str) -> (list):
    """Splits a given array into three parts, based off of the given pivot

    :param arr: The array to be partitioned
    :type arr: list[Rating]
    :param pivot: The pivot point for the partitions
    :type pivot: Rating
    :return: Returns a tuple of 3 lists of Ratings
    :rtype: (list[Rating])

    Author: Originally Sean Strout, edited by Thomas Schollenberger
    """

    left, middle, right = [], [], []
    for elm in arr:
        try:
            if elm.__getattribute__(prop) > pivot.__getattribute__(prop):
                right.append(elm)
            elif elm.__getattribute__(prop) < pivot.__getattribute__(prop):
                left.append(elm)
            else:
                middle.append(elm)
        except:
            raise Exception(f"Property doesn't exist on class {elm.__class__.__name__}")

    return left, middle, right


def quick_sort_class(arr: list, prop: str) -> list:
    """Performs the quick sort sorting algorithm. In most cases, it will be O(n log n)
    Uses sneaky hidden class methods to work for any class properties. Avoids having to have individual functions for each class prop

    :param arr: Array to be sorted
    :type arr: list[Rating]
    :return: New sorted array
    :rtype: Rating

    Author: Originally Sean Strout, edited by Thomas Schollenberger
    """

    if len(arr) == 0:
        return []
    else:
        left, middle, right = _part_rating_class(arr, arr[0], prop)
        return quick_sort_class(left, prop) + middle + quick_sort_class(right, prop)


def quick_sort_most_votes_name(arr: list[list]) -> list[list]:
    """Performs the quick sort sorting algorithm. In most cases, it will be O(n log n)

    :param arr: Array to be sorted
    :type arr: list[Rating]
    :return: New sorted array
    :rtype: Rating

    Author: Originally Sean Strout, edited by Thomas Schollenberger
    """

    if len(arr) == 0:
        return []
    else:
        left, middle, right = _part_most_votes_name(arr, arr[0][1])
        return quick_sort_most_votes_name(left) + middle + quick_sort_most_votes_name(right)

def _part_most_votes(arr: list[list], pivot: Rating) -> (list[list]):
    """Splits a given array into three parts, based off of the given pivot

    :param arr: The array to be partitioned
    :type arr: list[Rating]
    :param pivot: The pivot point for the partitions
    :type pivot: Rating
    :return: Returns a tuple of 3 lists of Ratings
    :rtype: (list[Rating])

    Author: Originally Sean Strout, edited by Thomas Schollenberger
    """

    left, middle, right = [], [], []
    for elm in arr:
        if elm[0].average_rating > pivot.average_rating:
            right.append(elm)
        elif elm[0].average_rating < pivot.average_rating:
            left.append(elm)
        else:
            middle.append(elm)

    if len(middle) > 1:
        middle = quick_sort_most_votes_name(middle)

    return left, middle, right


def quick_sort_most_votes(arr: list[list]) -> list[list]:
    """Performs the quick sort sorting algorithm. In most cases, it will be O(n log n)

    :param arr: Array to be sorted
    :type arr: list[Rating]
    :return: New sorted array
    :rtype: Rating

    Author: Originally Sean Strout, edited by Thomas Schollenberger
    """

    if len(arr) == 0:
        return []
    else:
        left, middle, right = _part_most_votes(arr, arr[0][0])
        return quick_sort_most_votes(left) + middle + quick_sort_most_votes(right)