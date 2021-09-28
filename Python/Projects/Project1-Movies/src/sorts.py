"""
    The sorts file provides two different types of methods. The first is query specific quick sorts, like for top and most_votes.
    The second is for accessing any method of a class and sorting by that (see _part_rating_class where we use __getattribute__)
    
    Author: Tom Schollenberger, with modified Sean Strout code
"""

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
            raise Exception(f"Property {prop} doesn't exist on class {elm.__class__.__name__}")

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
    
def _part_rating_class_nested(arr: list[list[Rating, Movie]], pivot: Rating, prop: str, index: int) -> (list):
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
    for entry in arr:
        elm = entry[index]
        try:
            if elm.__getattribute__(prop) > pivot.__getattribute__(prop):
                right.append(entry)
            elif elm.__getattribute__(prop) < pivot.__getattribute__(prop):
                left.append(entry)
            else:
                middle.append(entry)
        except:
            raise Exception(f"Property {prop} doesn't exist on class {elm.__class__.__name__}")

    return left, middle, right


def quick_sort_class_nested(arr: list, prop: str, index) -> list:
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
        left, middle, right = _part_rating_class_nested(arr, arr[0][index], prop, index)
        return quick_sort_class_nested(left, prop, index) + middle + quick_sort_class_nested(right, prop, index)
    
MOVIE_ELM = 1
"""Place holder index variable"""
RATING_ELM = 0
"""Place holder index variable"""
def quick_sort_top(arr: list[list[(Rating, Movie)]]) -> list[list[(Rating, Movie)]]:
    """Performs the quick sort method for the top query"""
    return quick_sort_class_nested(quick_sort_class_nested(quick_sort_class_nested(arr, "primary_title", MOVIE_ELM), "num_votes", RATING_ELM), "average_rating", RATING_ELM)[::-1]


def quick_sort_most_votes(arr):
    """Performs the quick sort method for the most votes query"""
    return quick_sort_class_nested(quick_sort_class_nested(arr, "primary_title", MOVIE_ELM), "num_votes", RATING_ELM)[::-1]