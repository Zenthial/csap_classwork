"""
    Sunstar generates a star based off of user input. No negative numbers are allowed.

    Sample input:
        Number of sides: 10
        Length of initial side: 150
        Number of levels: 4
        Deflection angle: 45
        
        Total length is 2185.6601717798208
    
    Author: Thomas Schollenberger
"""


import turtle as t
import math
import re


FLOAT_EXPRESSION = "[+]?([0-9]*[.])?[0-9]+"
"""The regular expression to typecheck floats"""
INT_EXPRESSION = "[+]?([0-9])?[0-9]+"
"""The regular expression to typecheck ints"""
SPEED = 0
"""The speed of turtle"""
DEFAULT_POSITION = [-85, 250]
"""The default position of the turtle cursor. [-85, 250] allows for a side length of 150 to be fully in frame"""


def get_input(input_string: str, input_expression: str, expected_type: str) -> str:
    """
    Parses raw user input, type checking it using regular expressions against the passed regular expression.
    If the user input is the incorrect type, it warns the user they provided the wrong input type, and calls itself

    Args:
        input_string (str): The string that should be displayed when prompting for input
        input_expression (str): The regular expression that is used to type check the user input
        expected_type (str): The expected type, as a string, to print out when the user passes the incorrect type

    Returns:
        str: Returns the type checked string
    """
    
    received_input = input(input_string)
    matched = re.fullmatch(input_expression, received_input)
    if matched == None:
        print(f"Value must be a {expected_type}. You entered {received_input}.\n")
        return get_input(input_string, input_expression, expected_type)
    return received_input


def draw_side(side_length: float, levels: int, angle: float) -> float:
    """
    Draws one side of the object

    Args:
        side_length (float): The length of the line to be drawn
        levels (int): The amount of times to call draw_sides
        angle (float): The angle of rotation

    Returns:
        float: The total length of the side drawn
    """
    
    angle_rad = math.radians(angle)
    if levels == 1:
        t.forward(side_length)
        return side_length
    else:
        line_length = 0
        new_length = (side_length/4) / math.cos(angle_rad)
        
        t.forward(side_length/4)
        line_length += side_length/4
        t.left(angle)
        line_length += draw_side(new_length, levels-1, angle)
        t.right(angle*2)
        line_length += draw_side(new_length, levels-1, angle)
        t.left(angle)
        t.forward(side_length/4)
        line_length += side_length/4
        return line_length
    
    
def draw_sides(side_length: float, levels: int, angle: float, num_sides: int) -> float:
    """
    Draws the sun. Loops over num_sides and calls draw_side num_sides amount of times

    Args:
        side_length (float): The length of each side
        levels (int): The amount of times draw_side should call itself
        angle (float): The angle of rotation used
        num_sides (int): The amount of sides to be drawn

    Returns:
        float: Total perimeter of the object drawn
    """
    
    total_length = 0
    for _ in range(0, num_sides):
        total_length += draw_side(side_length, levels, angle)
        t.right(360/num_sides)
    return total_length

        
def setup():
    """
    Internal turtle setup function. Gets the cursor in the right place, sets the title and the speed
    """
    
    t.title("SUNSTAR")
    t.speed(SPEED)
    t.penup()
    t.setposition(DEFAULT_POSITION)
    t.pendown()
    
def main():
    """
        Takes typechecked user input and passes it to the draw_sides function
        Prints out total_length and halts until the user closes the program
    """
    setup()

    num_sides = int(get_input("Please provide the number of sides:\n", INT_EXPRESSION, "int"))
    length = float(get_input("Please provide the side length:\n", FLOAT_EXPRESSION, "float"))
    levels = int(get_input("Please provide the number of levels:\n", INT_EXPRESSION, "int"))
    if levels > 1:
        angle = float(get_input("Please provide the deflection angle:\n", FLOAT_EXPRESSION, "float"))
    else:
        angle = 0
    
    total_length = draw_sides(length, levels, angle, num_sides)
    print(f"This went {total_length} total distance")
    t.mainloop()
    
if __name__ == "__main__":
    main()