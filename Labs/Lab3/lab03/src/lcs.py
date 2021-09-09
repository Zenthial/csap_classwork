"""[summary]

    Author: Thomas Schollenberger
"""

from ontology import Ontology, Concept
from dataclasses import dataclass
from sys import argv

@dataclass
class Similarlity:
    concept1: Concept
    concept2: Concept
    similarity_score: int
    
def sim(concept1: Concept, concept2: Concept, onto: Ontology) -> Similarlity:
    lcs = binary_LCS(concept1, concept2, onto)
    s1 = len(concept1.getPathToTop())
    s2 = len(concept2.getPathToTop())
    s3 = len(lcs.getPathToTop())
    sim_score = round((s3/(s1+s2-s3)),3)
    # side effect, append to the similarities list
    return Similarlity(concept1, concept2, sim_score)

def part(arr, pivot):
    left, middle, right = [], [], []
    for elm in arr:
        if elm.similarity_score > pivot.similarity_score:
            right.append(elm)
        elif elm.similarity_score < pivot.similarity_score:
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

def binary_search(path: list[Concept], target: Concept, start: int, end: int, onto: Ontology) -> int:
    if start == end:
        return start
    mid_index = (start + end) // 2
    mid_value = path[mid_index]
    if onto.subsumes(mid_value, target):
        return binary_search(path, target, start, mid_index, onto)
    else:
        return binary_search(path, target, mid_index + 1, end, onto)

def binary_LCS(concept1: Concept, concept2: Concept, onto: Ontology) -> Concept:
    arr = concept1.getPathToTop()
    return arr[binary_search(arr, concept2, 0, len(arr), onto)]

def linear_LCS(concept1: Concept, concept2: Concept) -> Concept:
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

def parse_input() -> str:
    if len(argv) > 1:
        file_path = argv[1]
        return file_path
    else:
        print("Usage: python3 lcs.py filepath")
        return False

def main():
    file_path = parse_input()
    if file_path == False:
        return
    
    o = Ontology(file_path)
    
    sims = []
    concepts = o.getAllConcepts()
    for concept1 in concepts:
        for concept2 in concepts:
            sims.append(sim(concept1, concept2, o))
    
    sims = quick_sort(sims)
    for sim_class in sims:
        print(f"sim({sim_class.concept1.name}, {sim_class.concept2.name}) = {sim_class.similarity_score}")
    
if __name__ == "__main__":
    main()