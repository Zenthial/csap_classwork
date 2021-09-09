"""
    Given an onthology, this finds the similarity scores for every concept in the onthology
    Prints them out in sorted order

    Author: Thomas Schollenberger
"""

from ontology import Ontology, Concept
from dataclasses import dataclass
from sys import argv
from typing import TypeVar, Union

T = TypeVar("T")
"""Allow the use of generics"""

@dataclass
class Similarity:
    """Contains the similarity data between two concepts, used to sort off of and display how closely two concepts are related"""
    
    concept1: Concept
    concept2: Concept
    similarity_score: int
    
def sim(concept1: Concept, concept2: Concept, onto: Ontology) -> Similarity:
    """[summary]

    :param concept1: The concepts to be compared
    :type concept1: Concept
    :param concept2: The concepts to be compared
    :type concept2: Concept
    :param onto: The workspace's ontology, that the Similarity is being created from
    :type onto: Ontology
    :return: Creates a new Similarity dataclass
    :rtype: Similarity
    """

    lcs = binary_LCS(concept1, concept2, onto)
    s1 = len(concept1.getPathToTop())
    s2 = len(concept2.getPathToTop())
    s3 = len(lcs.getPathToTop())
    sim_score = round((s3/(s1+s2-s3)),3)

    return Similarity(concept1, concept2, sim_score)

def part(arr: list[T], pivot: T) -> (list[T]):
    """Splits a given array into three parts, based off of the given pivot

    :param arr: The array to be partitioned
    :type arr: list[T]
    :param pivot: The pivot point for the partitions
    :type pivot: T
    :return: Returns a tuple of 3 lists of type T
    :rtype: (list[T])
    """

    left, middle, right = [], [], []
    for elm in arr:
        if elm.similarity_score > pivot.similarity_score:
            right.append(elm)
        elif elm.similarity_score < pivot.similarity_score:
            left.append(elm)
        else:
            middle.append(elm)
    return left, middle, right

def quick_sort(arr: list[T]) -> list[T]:
    """Performs the quick sort sorting algorithm. In most cases, it will be O(n log n)

    :param arr: Array to be sorted
    :type arr: list[T]
    :return: New sorted array
    :rtype: T
    """

    if len(arr) == 0:
        return []
    else:
        left, middle, right = part(arr, arr[0])
        return quick_sort(left) + middle + quick_sort(right)

def binary_search(path: list[Concept], target: Concept, start: int, end: int, onto: Ontology) -> int:
    """Performs the binary search algorithm, recursively. Cannot fail due to the nature of an ontology

    :param path: The concept1 path, back to the first ancestor
    :type path: list[Concept]
    :param target: The concept we're looking for
    :type target: Concept
    :param start: Start index
    :type start: int
    :param end: End index
    :type end: int
    :param onto: The ontology class
    :type onto: Ontology
    :return: The index of where the LCS lies in the given path
    :rtype: int
    """

    if start == end:
        return start
    mid_index = (start + end) // 2
    mid_value = path[mid_index]
    if onto.subsumes(mid_value, target):
        return binary_search(path, target, start, mid_index, onto)
    else:
        return binary_search(path, target, mid_index + 1, end, onto)

def binary_LCS(concept1: Concept, concept2: Concept, onto: Ontology) -> Concept:
    """Wrapper method for binary_search"""

    arr = concept1.getPathToTop()
    return arr[binary_search(arr, concept2, 0, len(arr), onto)]

def linear_LCS(concept1: Concept, concept2: Concept) -> Concept:
    """Linear LCS search, replaced by the binary LCS search"""

    conecept1_path = concept1.getPathToTop()[::-1]
    conecept2_path = concept2.getPathToTop()[::-1]

    length = len(conecept1_path) if len(conecept1_path) < len(conecept2_path) else len(conecept2_path)
    lcs_index = 0
    for i in range(length):
        elm1 = conecept1_path[i]
        elm2 = conecept2_path[i]
        
        if elm1 == elm2:
            lcs_index = i
        else:
            break
    
    return conecept1_path[lcs_index]

def index_of(arr: list[T], elm: T) -> int:
    """Replicates the java Array.indexOf() method, which returns -1 if the item does not exist, rather than erroring

    :param arr: The array to be searched
    :type arr: list[T]
    :param elm: The element, of type T, to be searched in the array
    :type elm: T
    :return: Returns the index of the found element, or -1
    :rtype: int
    """

    index = -1
    for i in range(len(arr)):
        if arr[i] == elm:
            index = i
            break
    
    return index

def has_pair(one: Concept, two: Concept, pairs: list[list[Concept]]) -> bool:
    """[summary]

    :param one: The first item of the pair to be checked
    :type one: Concept
    :param two: The second item of the pair to be checked
    :type two: Concept
    :param pairs: The matrix of pairs passed
    :type pairs: list[list[Concept]]
    :return: Returns True if a pair is found, False if not
    :rtype: bool
    """

    for pair in pairs:
        i1 = index_of(pair, one)
        i2 = index_of(pair, two)
        if i1 > -1 and i2 > -1 and i1 != i2:
            return True

    return False

def parse_input() -> Union[str, bool]:
    """Checks to see if argv arguments were passed

    :return: Returns the file path, if it was provided, else returns False
    :rtype: Union[str, bool]
    """
    if len(argv) > 1:
        file_path = argv[1]
        return file_path
    else:
        print("Usage: python3 lcs.py filepath")
        return False

def main():
    """Program entry point"""
    file_path = parse_input()
    if file_path == False:
        return
    
    o = Ontology(file_path)
    
    sims: list[Similarity] = []
    used_pairs = []
    concepts = o.getAllConcepts()
    for concept1 in concepts:
        for concept2 in concepts:
            if not has_pair(concept1, concept2, used_pairs):
                pair = [concept1, concept2]
                used_pairs.append(pair)
                sims.append(sim(concept1, concept2, o))
    
    sims = quick_sort(sims)
    for sim_class in sims:
        print(f"sim({sim_class.concept1.name}, {sim_class.concept2.name}) = {sim_class.similarity_score}")
    
if __name__ == "__main__":
    main()