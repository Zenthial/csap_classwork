"""
    Sunstar
"""

from turtle import *
import math

ANGLE = 45
ANGLE_RAD = math.radians(ANGLE)

def draw_side(n: float, l: int) -> float:
    if l == 1:
        forward(n)
        return n
    else:
        line_length = 0
        new_length = (n/4) / math.cos(ANGLE_RAD)
        
        forward(n/4)
        line_length += n/4
        left(ANGLE)
        line_length += draw_side(new_length, l-1)
        right(ANGLE*2)
        line_length += draw_side(new_length, l-1)
        left(ANGLE)
        forward(n/4)
        line_length += n/4
        return line_length
        
def setup():
    title("SUNSTAR")
    speed(0)
    
def main():
    length = int(input("Please provide side length:\n"))
    level = int(input("Please provide level:\n"))
    total_length = draw_side(length, level)
    print(f"This went {total_length} total distance")
    mainloop()
    
if __name__ == "__main__":
    setup()
    main()