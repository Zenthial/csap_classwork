from dataclasses import dataclass


@dataclass(frozen=True)
class Rating:
    tconst: str
    average_rating: float
    num_votes: int

    
@dataclass(frozen=True)
class Movie:
    tconst: str
    title_type: str
    primary_title: str
    original_title: str
    start_year: int
    end_year: int
    runtime: int
    genres: list[str]
    
rat = Rating("test", 1.0, 1)
print(rat.__getattribute__("tconst"))
try:
    print(rat.__getattribute__("test"))
except:
    print("uh oh")

print(rat.__class__.__name__)