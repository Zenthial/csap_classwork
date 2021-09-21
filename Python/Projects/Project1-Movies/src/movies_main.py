import reader
import processor
import sys

def main():
    small = False
    if len(sys.argv) == 2:
        if sys.argv[1] == "small":
            small = True

    movies_dict, rating_dict = reader.read_data(small)
    print(f"Total movies: {len(movies_dict)}")
    print(f"Total ratings: {len(rating_dict)}\n")
    processor.parse_input(movies_dict, rating_dict)
    
if __name__ == "__main__":
    main()