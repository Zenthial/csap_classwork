from ontology import Ontology, Concept
from dataclasses import dataclass

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
    

def main():
    o = Ontology("../data/simple.kb")
    
    cat = o.getConcept("cat")
    reptile = o.getConcept("reptile")
    mammal = o.getConcept("mammal")
    dog = o.getConcept("dog")
    
    print(linear_LCS(cat, mammal))
    print(linear_LCS(dog, reptile))
    
if __name__ == "__main__":
    main()