from classes import Movie, Rating
from time import time
from threading import Thread

TITLE_BASICS = "../data/title.basics.tsv"
TITLE_RATINGS = "../data/title.ratings.tsv"

def _get_movies_dict(movies: dict[str, Movie]):
    with open(TITLE_BASICS, encoding="utf8") as basics_file:
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

            movies[movie_info_array[0]] = Movie(movie_info_array[0], movie_info_array[1], movie_info_array[2], movie_info_array[3], start_year, end_year, minutes, genres_array)
            line = basics_file.readline()
    
    return movies

def _get_ratings_dict(ratings: dict[str, Rating], movies: dict[str, Movie]):
    with open(TITLE_RATINGS, encoding="utf8") as ratings_file:
        line = ratings_file.readline() #skip the header
        line = ratings_file.readline()
        while line:
            rating_info_array = line.split("\t")

            if movies.get(rating_info_array[0]) != None:
                ratings[rating_info_array[0]] = Rating(rating_info_array[0], float(rating_info_array[1]), float(rating_info_array[2]))
            line = ratings_file.readline()
    
    return ratings
        
def _get_movies(movies):
    print(f"reading movies from {TITLE_BASICS}. this may take a while")
    start = time()
    _get_movies_dict(movies)
    print(f"elapsed movies time (s): {time() - start}")
    
    return movies

def _get_ratings(ratings, movies):
    print(f"reading ratings from {TITLE_RATINGS}. this may take a while")
    start = time()
    _get_ratings_dict(ratings, movies)
    print(f"elapsed ratings time (s): {time() - start}")
    
    return ratings

def read_data():
    movies = {}
    ratings = {}
    
    # movies_thread = Thread(target=_get_movies, args=(movies,))
    # ratings_thread = Thread(target=_get_ratings, args=(ratings,))
    
    # movies_thread.start()
    # ratings_thread.start()
    
    # print("reading from both files simultaneously")
    
    # ratings_thread.join()
    # movies_thread.join()
    
    _get_movies(movies)
    _get_ratings(ratings, movies)
    
    print(f"num movies: {len(movies)}")
    print(f"num ratings: {len(ratings)}")
    
    return movies, ratings