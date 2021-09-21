from classes import Movie, Rating
from timeit import default_timer as timer

TITLE_BASICS = "../data/title.basics.tsv"
TITLE_RATINGS = "../data/title.ratings.tsv"

SMALL_BASICS = "../data/small.basics.tsv"
SMALL_RATINGS = "../data/small.ratings.tsv"

def _get_movies_dict(movies: dict[str, Movie], small: bool):
    file = TITLE_BASICS if small == False else SMALL_BASICS
    print(f"reading {file} into dict...")
    with open(file, encoding="utf8") as basics_file:
        line = basics_file.readline() #skip the header
        line = basics_file.readline()
        while line:
            movie_info_array = line.strip("\n").split("\t")
            
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

            movies[movie_info_array[0]] = Movie(movie_info_array[0], movie_info_array[1], movie_info_array[2], movie_info_array[3], start_year, end_year, minutes, genres_array)
            line = basics_file.readline()
    
    return movies

def _get_ratings_dict(ratings: dict[str, Rating], movies: dict[str, Movie], small: bool):
    file = TITLE_RATINGS if small == False else SMALL_RATINGS
    print(f"reading {file} into dict...")
    with open(file, encoding="utf8") as ratings_file:
        line = ratings_file.readline() #skip the header
        line = ratings_file.readline()
        while line:
            rating_info_array = line.strip("\n").split("\t")

            if movies.get(rating_info_array[0]) != None:
                ratings[rating_info_array[0]] = Rating(rating_info_array[0], float(rating_info_array[1]), int(rating_info_array[2]))
            line = ratings_file.readline()
    
    return ratings
        
def _get_movies(movies: dict[str, Movie], small: bool):
    start = timer()
    _get_movies_dict(movies, small)
    print(f"elapsed movies time (s): {timer() - start}\n")
    
    return movies

def _get_ratings(ratings: dict[str, Rating], movies: dict[str, Movie], small: bool):
    start = timer()
    _get_ratings_dict(ratings, movies, small)
    print(f"elapsed ratings time (s): {timer() - start}\n")
    
    return ratings

def read_data(small: bool):
    movies = {}
    ratings = {}
    
    _get_movies(movies, small)
    _get_ratings(ratings, movies, small)
    
    return movies, ratings