from dataclasses import dataclass


@dataclass
class Rating:
    tconst: str
    average_rating: float
    num_votes: int

    
@dataclass
class Movie:
    tconst: str
    title_type: str
    primary_title: str
    original_title: str
    start_year: int
    end_year: int
    runtime: int
    genres: list[str]
    
