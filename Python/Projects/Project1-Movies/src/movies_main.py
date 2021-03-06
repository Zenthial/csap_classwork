""" This project processed the over 8 million lines of IMDB data and performs 6 different queries on the data.
    This project takes command line arguments and piped queries as instructions.

    ALL FILE PATHS ASSUME YOU ARE INSIDE THE SRC FILE, WITH THE DATA FOLDER IN THE SRC PARENT DIRECTORY
    
    Author: Thomas Schollenberger
"""

import reader
import processor
import sys

def main():
    """Main entry point of the program"""
    small = False
    if len(sys.argv) == 2:
        if sys.argv[1] == "small":
            small = True
        
    if small:
        file_name = "../output/actual/out_small.txt"
    else:
        file_name = "../output/actual/out.txt"
        
    f = open(file_name, "w", encoding="utf8")
    
    print(f"Running. Writing contents to {file_name}")

    movies_dict, rating_dict = reader.read_data(small, f)
    movies_out = f"Total movies: {len(movies_dict)}\n"
    ratings_out = f"Total ratings: {len(rating_dict)}\n\n"
    f.write(movies_out)
    f.write(ratings_out)
    print(movies_out)
    print(ratings_out)
    processor.parse_input(movies_dict, rating_dict, f)
    
    print("Contents written")
    
if __name__ == "__main__":
    main()