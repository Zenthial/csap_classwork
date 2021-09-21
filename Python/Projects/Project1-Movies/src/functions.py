from classes import Movie, Rating
from timeit import default_timer as timer
import sorts

def get_movie_info(movie_info: Movie) -> str:
    return f"Identifier: {movie_info.tconst}, Title: {movie_info.primary_title}, Type: {movie_info.title_type}, Year: {movie_info.start_year}, Runtime: {movie_info.runtime}, Genres: {', '.join(movie_info.genres)}"

def get_rating_info(rating_info: Rating) -> str:
    return f"Identifier: {rating_info.tconst}, Rating: {rating_info.average_rating}, Votes: {rating_info.num_votes}"

def lookup(movies: dict[str, Movie], ratings: dict[str, Rating], tconst: str):
    print(f"LOOKUP {tconst}")
    start = timer()
    movie_info = movies.get(tconst)
    rating_info = ratings.get(tconst)

    if movie_info is not None and rating_info is not None:
        print(f"\tMOVIE: {get_movie_info(movie_info)}")
        print(f"\tRATING: {get_rating_info(rating_info)}")
    else:
        print("Movie not found!\nRating not found!")
    
    print(f"elapsed timer (s): {timer() - start}\n")

def contains(movies: dict[str, Movie], title_type: str, words: str):
    print(f"CONTAINS {title_type} {words}")
    start = timer()
    matches = []
    for movie_info in movies.values():
        if movie_info.title_type == title_type and movie_info.primary_title.find(words) != -1:
            matches.append(movie_info)
        
    if len(matches) == 0:
        print("\tNo match found!")
    else:
        # matches = sorts.quick_sort_class(matches, "primary_title") # per discussions, don't need to sort alphabetically
        for match in matches:
            print(f"\t{get_movie_info(match)}")

    
    print(f"elapsed timer (s): {timer() - start}\n")

def year_and_genre(movies: dict[str, Movie], title_type: str, start_year: int, genre: str):
    print(f"YEAR_AND_GENRE {title_type} {start_year} {genre}")
    start = timer()
    matches = 0
    found_movies: list[Movie] = []
    for movie_info in movies.values():
        if movie_info.title_type == title_type and movie_info.start_year == start_year and genre in movie_info.genres:
            found_movies.append(movie_info)
            matches += 1

    found_movies = sorts.quick_sort_class(found_movies, "primary_title")

    for movie in found_movies:
        print(f"\t{get_movie_info(movie)}")

    if matches == 0:
        print("\tNo match found!")
    
    print(f"elapsed timer (s): {timer() - start}\n")

def runtime(movies: dict[str, Movie], title_type: str, start_time: int, end_time: int):
    print(f"RUNTIME {title_type} {start_time} {end_time}")
    start = timer()
    matches = []
    for movie_info in movies.values():
        if movie_info.title_type == title_type and movie_info.runtime >= start_time and movie_info.runtime <= end_time:
            matches.append(movie_info)

    matches = sorts.quick_sort_class(matches, "runtime")

    if len(matches) == 0:
        print("\tNo match found!")
    else:
        for match in matches:
            print(f"\t{get_movie_info(match)}")
    
    print(f"elapsed timer (s): {timer() - start}\n")

def most_votes(movies: dict[str, Movie], ratings: list[Rating], title_type: str, count: int):
    print(f"MOST_VOTES {title_type} {count}")
    start = timer()
    
    filtered = [x for x in ratings if movies.get(x.tconst).title_type == title_type][::-1][:count]
    matches = []
    for rating in filtered:
        movie_info = movies.get(rating.tconst)
        matches.append([rating, movie_info])
            
    matches = sorts.quick_sort_most_votes(matches)
    
    output = ""
    if len(matches) == 0:
        output += "\tNo match found!"
    else:
        i = 1
        for entry in matches:
            output += f"\t{i}. VOTES: {entry[0].num_votes}, MOVIE: {get_movie_info(entry[1])}"
            i += 1
            if i < count+1:
                output += "\n"
    
    print(output)
    print(f"elapsed timer (s): {timer() - start}\n")

def top(movies: dict[str, Movie], ratings: dict[str, Rating], title_type: str, count: int, start_year: int, end_year: int):
    print(f"TOP {title_type} {count} {start_year} {end_year}")
    start = timer()
    
    years = list(range(start_year, end_year+1))
    years_dict: dict[int, list[str]] = {}
    years_count: dict[int, int] = {}

    for year in years:
        years_dict[year] = []
        years_count[year] = 0

    for rating in ratings.values():
        movie_info = movies.get(rating.tconst)
        if rating.num_votes > 1000:
            if movie_info.start_year in years and years_count[movie_info.start_year] < count:
                years_count[movie_info.start_year] += 1
                years_dict[movie_info.start_year].append([rating, movie_info])
                
    for year in years:
        if len(years_dict[year]) != 0:
            years_dict[year] = sorts.quick_sort_top(years_dict[year])

    for year in years_dict:
        print(f"\tYEAR: {year}")
        entry = years_dict[year]
        if len(entry) > 0:
            i = 1
            for line in entry:
                print(f"\t\t{i}. RATING: {line[0].average_rating}, VOTES: {line[0].num_votes}, MOVIE: {get_movie_info(line[1])}")
                i += 1
        else:
            print("\t\tNo match found!")
    
    print(f"elapsed timer (s): {timer() - start}\n")