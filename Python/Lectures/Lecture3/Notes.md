# Week 3: Lists and Sets, Sorting, Command Line Arguments, Dataclasses
List = normal array in scripting languages
List methods, refer to list_demo.py

## Getting Command Line Arguments
```py
import sys

sys.argv # this is a list

if len(sys.argv) != 0:
    for arg in sys.argv:
        print(arg)
```

## Sorting
sorts.py

## Dataclasses
```py
from dataclasses import dataclass

@dataclass
class RIT_building:
    name: str
    number: int

computing = RIT_building("Golisano", 70)
union = RIT_building("Student Alumni Building", 4)
```