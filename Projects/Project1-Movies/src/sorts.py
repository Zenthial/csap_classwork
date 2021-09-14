from classes import Rating, Movie

def part_votes(arr: list[Rating], pivot: Rating) -> (list[Rating]):
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
        if elm.num_votes > pivot.num_votes:
            right.append(elm)
        elif elm.num_votes < pivot.num_votes:
            left.append(elm)
        else:
            middle.append(elm)
    return left, middle, right


def quick_sort_votes(arr: list[Rating]) -> list[Rating]:
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
        left, middle, right = part_votes(arr, arr[0])
        return quick_sort_votes(left) + middle + quick_sort_votes(right)

def part_name(arr: list[Movie], pivot: Movie) -> (list[Movie]):
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
        if elm.primary_title > pivot.primary_title:
            right.append(elm)
        elif elm.primary_title < pivot.primary_title:
            left.append(elm)
        else:
            middle.append(elm)
    return left, middle, right


def quick_sort_name(arr: list[Movie]) -> list[Movie]:
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
        left, middle, right = part_name(arr, arr[0])
        return quick_sort_name(left) + middle + quick_sort_name(right)

def part_runtime(arr: list[Movie], pivot: Movie) -> (list[Movie]):
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
        if elm.runtime < pivot.runtime:
            right.append(elm)
        elif elm.runtime > pivot.runtime:
            left.append(elm)
        else:
            middle.append(elm)
    
    if len(middle) > 1:
        middle = quick_sort_name(middle)

    return left, middle, right


def quick_sort_runtime(arr: list[Movie]) -> list[Movie]:
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
        left, middle, right = part_runtime(arr, arr[0])
        return quick_sort_runtime(left) + middle + quick_sort_runtime(right)


def part_rating(arr: list[Rating], pivot: Rating) -> (list[Rating]):
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
        if elm.average_rating > pivot.average_rating:
            right.append(elm)
        elif elm.average_rating < pivot.average_rating:
            left.append(elm)
        else:
            middle.append(elm)

    if len(middle) > 1:
        middle = quick_sort_votes(middle)

    return left, middle, right


def quick_sort_rating(arr: list[Rating]) -> list[Rating]:
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
        left, middle, right = part_rating(arr, arr[0])
        return quick_sort_rating(left) + middle + quick_sort_rating(right)

def part_most_votes_name(arr: list[list], pivot: Movie) -> (list[list]):
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
        if elm[1].primary_title > pivot.primary_title:
            right.append(elm)
        elif elm[1].primary_title < pivot.primary_title:
            left.append(elm)
        else:
            middle.append(elm)

    return left, middle, right


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
        left, middle, right = part_most_votes_name(arr, arr[0][1])
        return quick_sort_most_votes_name(left) + middle + quick_sort_most_votes_name(right)

def part_most_votes(arr: list[list], pivot: Rating) -> (list[list]):
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
        left, middle, right = part_most_votes(arr, arr[0][0])
        return quick_sort_most_votes(left) + middle + quick_sort_most_votes(right)