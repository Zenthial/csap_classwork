"""
    Given an ontology, this finds the similarity scores for every concept in the ontology
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

    Author: Originally Sean Strout, edited by Thomas Schollenberger
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

    Author: Originally Sean Strout, edited by Thomas Schollenberger
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

    concept1_path = concept1.getPathToTop()[::-1]
    concept2_path = concept2.getPathToTop()[::-1]

    length = len(concept1_path) if len(concept1_path) < len(concept2_path) else len(concept2_path)
    lcs_index = 0
    for i in range(length):
        elm1 = concept1_path[i]
        elm2 = concept2_path[i]
        
        if elm1 == elm2:
            lcs_index = i
        else:
            break
    
    return concept1_path[lcs_index]


def parse_input() -> Union[str, bool]:
    """Checks to see if argv arguments were passed

    :return: Returns the file path, if it was provided, else returns False
    :rtype: Union[str, bool]
    """

    if len(argv) == 2:
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
    concepts = o.getAllConcepts()
    for i in range(len(concepts)):
        for j in range(i, len(concepts)):
            concept1 = concepts[i]
            concept2 = concepts[j]
            sims.append(sim(concept1, concept2, o))
                
    
    sims = quick_sort(sims)
    # uncomment this if you want to write to a file
    # with open("./output.txt", "w") as file_to_write:
    #     for sim_class in sims:
    #         file_to_write.write(f"Sim( {sim_class.concept1.name} , {sim_class.concept2.name} ) =  {sim_class.similarity_score}\n") # this has the same format as the sample simscores
    #         print(f"sim({sim_class.concept1.name}, {sim_class.concept2.name}) = {sim_class.similarity_score}") # this has the better formatting
    
    for sim_class in sims:
        print(f"Sim( {sim_class.concept1.name} , {sim_class.concept2.name} ) =  {sim_class.similarity_score}") # this has the better formatting
            
    #print("Written to output.txt for your convenience in checking sample outputs vs this program's. Done because when running phys-object.kb, it outputs more lines than the console can hold.")

    
if __name__ == "__main__":
    main()