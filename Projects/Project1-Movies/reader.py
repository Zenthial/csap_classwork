from typing_extensions import Unpack
from classes import Movie, Rating
from time import time

TITLE_BASICS = "../data/title.basics.tsv"
TITLE_RATINGS = "../data/title.ratings.tsv"

def _get_basics_dict():
    basics: dict[str, Movie] = {}
    with open(TITLE_BASICS) as basics_file:
        line = basics_file.readline() #skip the header
        line = basics_file.readline()
        while line:
            movie_info_array = line.split("\t")
            
            if movie_info_array[4] == "1":
                continue
            
            genres_array = movie_info_array[8].split(",")
            basics[movie_info_array[0]] = Movie(movie_info_array[0], movie_info_array[1], movie_info_array[2], movie_info_array[3], int(movie_info_array[5]), int(movie_info_array[6]), int(movie_info_array[7]), genres_array)
            line = basics_file.readline()
    
    return basics
        

def read_data():
    print(f"reading movies from {TITLE_BASICS}")
    start = time()
    basics = _get_basics_dict()
    end = time() - start
    print(f"elapsed time: {end}")

    print(f"Num Movies {len(basics)}")
    
    return basics