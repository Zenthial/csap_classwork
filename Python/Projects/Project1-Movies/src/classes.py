""" This file defines the two dataclasses used to store each entry from each file

    Author: Thomas Schollenberger
"""

from dataclasses import dataclass


@dataclass(frozen=True)
class Rating:
    """The Rating Dataclass stores all the needed datafields from the ratings files"""
    tconst: str
    """The id of the movie"""
    average_rating: float
    """The average rating given to the movie by reviewers"""
    num_votes: int
    """The number of unique ratings"""

    
@dataclass(frozen=True)
class Movie:
    """The Movie Dataclass stores all needed datafields from the movie files"""
    tconst: str
    """The id of the movie"""
    title_type: str
    """Type of the movie/show/video game"""
    primary_title: str
    """The primary title that is shown in publication"""
    original_title: str
    """A previous working title, if there was one"""
    start_year: int
    """The year the Movie was created"""
    end_year: int
    """The end year, if the Movie/Show lasted more than one year"""
    runtime: int
    """Length of the Movie, in minutes"""
    genres: list[str]
    """List of all the different related genres associated with the Movie"""