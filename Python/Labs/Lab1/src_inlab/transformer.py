"""
CSAPX Lab 1: Secret Messages

A program that encodes/decodes a message by applying a set of transformation operations.
The transformation operations are:
    shift - Sa[,n] changes letter at index a by moving it n letters fwd in the alphabet
    rotate - R[n] rotates the string n positions to the right
    duplicate - Da[,n] follows character at index a with n copies of itself
    trade - T[(g)]a,b swaps the places of the a-th and b-th groups of g total

All indices and group numbers are 0-based.

author: Thomas Schollenberger
"""

print_statement = "Operation Shift ({}) - Parameters: "

def shift(msg, transformation):
    print(print_statement.format("S") + transformation[1:])
    
def rotate(msg, transformation):
    print(print_statement.format("R") + transformation[1:])
    
def duplicate(msg, transformation):
    print(print_statement.format("D") + transformation[1:])
    
def trade(msg, transformation):
    print(print_statement.format("T") + transformation[1:])

transformation_types = ["S", "R", "D", "T"]
def find_transformation_type(transformation):
    for trans_type in transformation_types:
        if transformation.find(trans_type) != -1:
            return trans_type
    return "N"

def transform(msg, transformation):
    split_transformations = transformation.split(";")
    for trans in split_transformations:
        trans_type = find_transformation_type(trans)
        if trans_type == "S":
            shift(msg, trans)
        elif trans_type == "R":
            rotate(msg, trans)
        elif trans_type == "D":
            duplicate(msg, trans)
        elif trans_type == "T":
            trade(msg, trans)

def main() -> None:
    """
    The main program is responsible for getting the input details from user and writing the output file with the results
    of encrypting or decrypting the message file applying the transformations from the operation file.
    :return: None
    """
    
    message_file = input("Please provide a file to read messages from \n")
    operations_file = input("Please provide a file to read operations from \n")
    
    print("Generating output ...")
    with open(message_file) as messages, open(operations_file) as operations:
        message_line = messages.readline().replace("\n", "")
        operation_line = operations.readline().replace("\n", "")
        while message_line != "" and operation_line != "":
            print('Transforming Message: '+ message_line)
            print('Applying...')
            transform(message_line, operation_line)
            message_line = messages.readline().replace("\n", "")
            operation_line = operations.readline().replace("\n", "")
            print("\n")


if __name__ == '__main__':
    main()
