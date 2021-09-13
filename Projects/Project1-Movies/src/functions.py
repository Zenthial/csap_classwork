from classes import Movie, Rating
from time import time


def lookup(movies: dict[str, Movie], ratings: dict[str, Rating], tconst: str):
    print(f"LOOKUP {tconst}")
    start = time()
    movie_info = movies.get(tconst)
    rating_info = ratings.get(tconst)

    if movie_info is not None and rating_info is not None:
        print(f"MOVIE: Identifier: {movie_info.tconst}, Title: {movie_info.primary_title}, Type: {movie_info.title_type}. Year: {movie_info.start_year}, Runtime: {movie_info.runtime}, Genres: {movie_info.genres}")
        print(f"RATING: Identifier: {rating_info.tconst}, Rating: {rating_info.average_rating}, Votes: {rating_info.num_votes}")
    else:
        print("Movie not found!\nRating not found!")
    
    print(f"elapsed time (s): {time() - start}")
