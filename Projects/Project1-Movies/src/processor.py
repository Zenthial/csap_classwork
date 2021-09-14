import sys
import functions
import sorts

from classes import Movie, Rating

def _parse_line(line: str, movies: dict[str, Movie], ratings: dict[str, Rating], sorted_votes: list[Rating], sorted_ratings: list[Rating]):
    line_array = line.strip("\n").split(" ")
    type = line_array[0]
    print("processing: ", end="")
    if type == "LOOKUP":
        tconst = line_array[1]
        functions.lookup(movies, ratings, tconst)
    elif type == "CONTAINS":
        title_type = line_array[1]
        words = line_array[2]
        functions.contains(movies, title_type, words)
    elif type == "YEAR_AND_GENRE":
        title_type = line_array[1]
        start_year = int(line_array[2])
        genre = line_array[3]
        functions.year_and_genre(movies, title_type, start_year, genre)
    elif type == "RUNTIME":
        title_type = line_array[1]
        start_time = int(line_array[2])
        end_time = int(line_array[3])
        functions.runtime(movies, title_type, start_time, end_time)
    elif type == "MOST_VOTES":
        title_type = line_array[1]
        count = int(line_array[2])
        functions.most_votes(movies, sorted_votes, title_type, count)
    elif type == "TOP":
        title_type = line_array[1]
        count = int(line_array[2])
        start_year = int(line_array[3])
        end_year = int(line_array[4])
        functions.top(movies, sorted_ratings, title_type, count, start_year, end_year)


def parse_input(movies: dict[str, Movie], ratings: dict[str, Rating]):
    ratings_list = []
    for rating in ratings.values():
        ratings_list.append(rating)

    sorted_votes = sorts.quick_sort_votes(ratings_list[::])
    sorted_ratings = sorts.quick_sort_rating(ratings_list[::])

    input = sys.stdin
    line = input.readline()
    while line:
        _parse_line(line, movies, ratings, sorted_votes, sorted_ratings)
        line = input.readline()