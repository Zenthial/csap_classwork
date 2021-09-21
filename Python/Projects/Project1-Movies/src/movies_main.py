import reader
import processor
import sys

def main():
    small = False
    if len(sys.argv) == 2:
        if sys.argv[1] == "small":
            small = True
        
    if small:
        file_name = "../output/actual/out_small.txt"
    else:
        file_name = "../output/actual/out.txt"
        
    f = open(file_name, "w")
    
    print(f"Running. Writing contents to {file_name}")

    movies_dict, rating_dict = reader.read_data(small, f)
    f.write(f"Total movies: {len(movies_dict)}\n")
    f.write(f"Total ratings: {len(rating_dict)}\n\n")
    processor.parse_input(movies_dict, rating_dict, f)
    
    print("Contents written")
    
if __name__ == "__main__":
    main()