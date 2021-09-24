from io import TextIOWrapper
from classes import Movie, Rating
from timeit import default_timer as timer

TITLE_BASICS = "../data/title.basics.tsv"
TITLE_RATINGS = "../data/title.ratings.tsv"

SMALL_BASICS = "../data/small.basics.tsv"
SMALL_RATINGS = "../data/small.ratings.tsv"

def _get_movies_dict(movies: dict[str, Movie], small: bool, f: TextIOWrapper):
    file = TITLE_BASICS if small == False else SMALL_BASICS
    out = f"reading {file} into dict...\n"
    f.write(out)
    print(out)
    with open(file, encoding="utf8") as basics_file:
        i = 0
        for line in basics_file:
            if i == 0:
                i += 1
                continue

            movie_info_array = line.strip("\n").split("\t")
            
            if movie_info_array[4] == "1":
                continue
            
            genres_array = movie_info_array[8].split(",")
            if movie_info_array[8].find("\\N") != -1:
                genres_array = ["None"]
                
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

            movies[movie_info_array[0]] = Movie(movie_info_array[0], movie_info_array[1], movie_info_array[2], movie_info_array[3], start_year, end_year, minutes, genres_array)
    
    return movies

def _get_ratings_dict(ratings: dict[str, Rating], movies: dict[str, Movie], small: bool, f: TextIOWrapper):
    file = TITLE_RATINGS if small == False else SMALL_RATINGS
    out = f"reading {file} into dict...\n"
    f.write(out)
    print(out)
    with open(file, encoding="utf8") as ratings_file:
        i = 0
        for line in ratings_file:
            if i == 0:
                i += 1
                continue

            rating_info_array = line.strip("\n").split("\t")

            if movies.get(rating_info_array[0]) != None:
                ratings[rating_info_array[0]] = Rating(rating_info_array[0], float(rating_info_array[1]), int(rating_info_array[2]))
    
    return ratings
        
def _get_movies(movies: dict[str, Movie], small: bool, f: TextIOWrapper):
    start = timer()
    _get_movies_dict(movies, small, f)
    out = f"elapsed time (s): {timer() - start}\n\n"
    f.write(out)
    print(out)
    
    return movies

def _get_ratings(ratings: dict[str, Rating], movies: dict[str, Movie], small: bool, f: TextIOWrapper):
    start = timer()
    _get_ratings_dict(ratings, movies, small, f)
    out = f"elapsed time (s): {timer() - start}\n\n"
    f.write(out)
    print(out)
    
    return ratings

def read_data(small: bool, f: TextIOWrapper):
    movies = {}
    ratings = {}
    
    _get_movies(movies, small, f)
    _get_ratings(ratings, movies, small, f)
    
    return movies, ratings