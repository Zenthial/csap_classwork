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
                line = basics_file.readline()
                continue
            
            genres_array = movie_info_array[8].split(",")
            start_year = movie_info_array[5]
            if start_year == "\\N":
                start_year = 0
            else:
                start_year = int(start_year)

            end_year = movie_info_array[6]
            if end_year == "\\N":
                end_year = 0
            else:
                end_year = int(end_year)

            minutes = movie_info_array[7]
            if minutes == "\\N":
                minutes = 0
            else:
                minutes = int(minutes)

            basics[movie_info_array[0]] = Movie(movie_info_array[0], movie_info_array[1], movie_info_array[2], movie_info_array[3], start_year, end_year, minutes, genres_array)
            line = basics_file.readline()
    
    return basics
        

def read_data():
    print(f"reading movies from {TITLE_BASICS}. this may take a while")
    start = time()
    basics = _get_basics_dict()
    end = time() - start
    print(f"elapsed time (s): {end}")

    print(f"num movies {len(basics)}")
    
    return basics