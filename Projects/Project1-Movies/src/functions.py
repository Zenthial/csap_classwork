from classes import Movie, Rating
from timeit import default_timer as timer
import sorts

def get_movie_info(movie_info: Movie) -> str:
    return f"Identifier: {movie_info.tconst}, Title: {movie_info.primary_title}, Type: {movie_info.title_type}, Year: {movie_info.start_year}, Runtime: {movie_info.runtime}, Genres: {', '.join(movie_info.genres)}"

def get_rating_info(rating_info: Rating) -> str:
    return f"Identifier: {rating_info.tconst}, Rating: {rating_info.average_rating}, Votes: {rating_info.num_votes}"

def lookup(movies: dict[str, Movie], ratings: dict[str, Rating], tconst: str):
    return_str = ""
    return_str += f"LOOKUP {tconst}\n"
    start = timer()
    movie_info = movies.get(tconst)
    rating_info = ratings.get(tconst)

    if movie_info is not None and rating_info is not None:
        return_str += f"\tMOVIE: {get_movie_info(movie_info)}\n"
        return_str += f"\tRATING: {get_rating_info(rating_info)}\n"
    else:
        return_str += "\tMovie not found!\nRating not found!\n"
    
    return_str += f"elapsed time (s): {timer() - start}\n\n"
    return return_str

def contains(movies: dict[str, Movie], title_type: str, words: str):
    return_str = ""
    return_str += f"CONTAINS {title_type} {words}\n"
    
    start = timer()
    matches = []
    for movie_info in movies.values():
        if movie_info.title_type == title_type and movie_info.primary_title.find(words) != -1:
            matches.append(movie_info)
        
    if len(matches) == 0:
        return_str += "\tNo match found!\n"
    else:
        # matches = sorts.quick_sort_class(matches, "primary_title") # per discussions, don't need to sort alphabetically
        for match in matches:
            return_str += f"\t{get_movie_info(match)}\n"

    
    return_str += f"elapsed time (s): {timer() - start}\n\n"
    return return_str

def year_and_genre(movies: dict[str, Movie], title_type: str, start_year: int, genre: str):
    return_str = ""
    return_str += f"YEAR_AND_GENRE {title_type} {start_year} {genre}\n"
    start = timer()
    matches = 0
    found_movies: list[Movie] = []
    for movie_info in movies.values():
        if movie_info.title_type == title_type and movie_info.start_year == start_year and genre in movie_info.genres:
            found_movies.append(movie_info)
            matches += 1

    found_movies = sorts.quick_sort_class(found_movies, "primary_title")

    if matches == 0:
        return_str += "\tNo match found!\n"
    else:
        for movie in found_movies:
            return_str += f"\t{get_movie_info(movie)}\n"
    
    return_str += f"elapsed time (s): {timer() - start}\n\n"
    return return_str

def runtime(movies: dict[str, Movie], title_type: str, start_time: int, end_time: int):
    return_str = ""
    return_str += f"RUNTIME {title_type} {start_time} {end_time}\n"
    start = timer()
    matches = []
    for movie_info in movies.values():
        if movie_info.title_type == title_type and movie_info.runtime >= start_time and movie_info.runtime <= end_time:
            matches.append(movie_info)

    matches = sorts.quick_sort_class(sorts.quick_sort_class(matches, "primary_title")[::-1], "runtime")[::-1]

    if len(matches) == 0:
        return_str += "\tNo match found!\n"
    else:
        for match in matches:
            return_str += f"\t{get_movie_info(match)}\n"
    
    return_str += f"elapsed time (s): {timer() - start}\n\n"
    return return_str

def most_votes(movies: dict[str, Movie], ratings: list[Rating], title_type: str, count: int):
    return_str = ""
    return_str += f"MOST_VOTES {title_type} {count}\n"
    start = timer()
    
    filtered = [x for x in ratings if movies.get(x.tconst).title_type == title_type][::-1][:count]
    matches = []
    for rating in filtered:
        movie_info = movies.get(rating.tconst)
        matches.append([rating, movie_info])
            
    matches = sorts.quick_sort_most_votes(matches)
    
    output = ""
    if len(matches) == 0:
        output += "\tNo match found!\n"
    else:
        i = 1
        for entry in matches:
            output += f"\t{i}. VOTES: {entry[0].num_votes}, MOVIE: {get_movie_info(entry[1])}\n"
            i += 1
    
    output += f"elapsed time (s): {timer() - start}\n\n"
    return_str += output
    
    return return_str

def top(movies: dict[str, Movie], ratings: dict[str, Rating], title_type: str, count: int, start_year: int, end_year: int):
    return_str = ""
    return_str += f"TOP {title_type} {count} {start_year} {end_year}\n"
    start = timer()
    
    rat_table = [x for x in ratings.values()]
    years = list(range(start_year, end_year+1))
    years_dict: dict[int, list[str]] = {}

    for year in years:
        tab = []
        cnt = 0
        for rating in rat_table:
            movie_info = movies.get(rating.tconst)
            if movie_info.start_year == year and movie_info.title_type == title_type and rating.num_votes >= 1000:
                tab.append([rating, movie_info])
                cnt += 1
                if cnt >= count:
                    break
                
        tab = sorts.quick_sort_top(tab)
        years_dict[year] = tab

    for year in years_dict:
        return_str += f"\tYEAR: {year}\n"
        entry = years_dict[year]
        if len(entry) > 0:
            i = 1
            for line in entry:
                return_str += f"\t\t{i}. RATING: {line[0].average_rating}, VOTES: {line[0].num_votes}, MOVIE: {get_movie_info(line[1])}\n"
                i += 1
        else:
            return_str += "\t\tNo match found!\n"
    
    return_str += f"elapsed time (s): {timer() - start}\n\n"
    
    return return_str