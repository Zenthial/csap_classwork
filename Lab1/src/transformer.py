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

def shift(msg: str, transformation: str, decrypt: bool) -> str:
    """Shifts a given character with the passed msg up by whatever number is given

    Args:
        msg (str): The message that needs to be encrypted or decrypted
        transformation (str): The transformation operation string
        decrypt (bool): Does the inverse of the operation passed if True

    Returns:
        (str): The new message, with the specified character increased by the amount given
    """
    
    input_data = transformation[1:].split(",")
    index = int(input_data[0])
    shift_amount = 1
    if len(input_data) > 1:
        shift_amount = int(input_data[1])
        
    if decrypt:
        shift_amount = -shift_amount
    
    character = msg[index]
    max_unicode = ord("Z")
    min_unicode = ord("A")
    character_unicode = ord(character)
    new_character = character_unicode + shift_amount
    if (new_character) > max_unicode:
        diff = new_character - max_unicode
        new_character = min_unicode + diff-1 # has to be minus one, else with a shift on Z where you want to go up one, you would jump to B
    elif (new_character) < min_unicode:
        diff = min_unicode - new_character
        new_character = max_unicode - diff+1
        
    new_msg = ''
    for i in range(len(msg)):
        if i == index:
            new_msg += chr(new_character)
        else:
            new_msg += msg[i]
    return new_msg
    
def rotate(msg: str, transformation: str, decrypt: bool) -> str:
    """Rotates the passed msg

    Args:
        msg (str): The msg to be rotated
        transformation (str): The operation str containing the rotation amount
        decrypt (bool): True applies the reverse of the transformation

    Returns:
        (str): The reversed msg
    """
    
    input_data = transformation[1:]
    exponent = 1
    if len(input_data) != 0:
        exponent = int(input_data)
    if decrypt:
        exponent = -exponent
    return msg[-exponent:] + msg[:-exponent]
    
def duplicate(msg: str, transformation: str, decrypt: bool) -> str:
    """Duplicates a character at a given index, K amount of times

    Args:
        msg (str): The msg containing the chr to be duplicated
        transformation (str): The transformation str containing the duplication amount and chr index
        decrypt (bool): Reverses the duplication if True

    Returns:
        (str): The msg containing the duplicated chr(s)
    """
    input_data = transformation[1:].split(",")
    index = int(input_data[0])
    times = 1
    if len(input_data) > 1:
        times = int(input_data[1])
    new_msg = ''
    if not decrypt:    
        for i in range(len(msg)):
            c = msg[i]
            if i == index:
                c *= times+1
            new_msg += c
    else:
        new_msg = msg[:index] + msg[index:index+times]
        if index + times + 1 < len(msg):
            new_msg += msg[index+times+1:]
    return new_msg

def exponent_trade(msg: str, exponent: int, first: int, second: int) -> str:
    """
    To only be called internally by the trade function

    Args:
        msg (str): The message that needs to be encrypted or decrypted
        exponent (int): Number of subgroups the msg should be split into
        first (int): First index to swap in the split up string array
        second (int): Second index to swap in the split up string array
        
    Returns:
        (str): The editted message with the exponential trade operation applied to it
    """
    separated_group = []
    length = len(msg)
    
    for i in range(0, length, length // exponent):
        separated_group.append(msg[i:i+exponent+1])
        
    first_swap = separated_group[first][::]
    second_swap = separated_group[second][::]
    separated_group[first] = second_swap
    separated_group[second] = first_swap
    new_msg = ''
    for word in separated_group:
        new_msg += word
    return new_msg
    
def normal_trade(msg: str, first: int, second: int) -> str:
    """The default trade, when no exponent is passed. To only be called internally by the trade function

    Args:
        msg (str): The message that needs to be encrypted or decrypted
        first (int): First index to swap in the split up string array
        second (int): Second index to swap in the split up string array
        
    Returns:
        (str): The editted message with the normal trade operation applied to it
    """
    first_chr = msg[first]
    second_chr = msg[second]
    new_msg = ''
    for i in range(len(msg)):
        if i == first:
            new_msg += second_chr
        elif i == second:
            new_msg += first_chr
        else:
            new_msg += msg[i]
        
    return new_msg
        
    
def trade(msg: str, transformation: str) -> str:
    input_data = transformation[1:].split(",")
    if input_data[0].find(")") != -1:
        # exponent
        new_split = input_data[0].split(")")
        exponent = int(new_split[0].strip("("))
        first = int(new_split[1])
        second = int(input_data[1])
        return exponent_trade(msg, exponent, first, second)
    else:
        return normal_trade(msg, int(input_data[0]), int(input_data[1]))
        

transformation_types = ["S", "R", "D", "T"]
def find_transformation_type(transformation: str) -> str:
    """Parses a string for it's transformation type

    Args:
        transformation (str): A str with one of the transformation types in it

    Returns:
        (str): A single chr corresponding to a transformation
    """
    for trans_type in transformation_types:
        if transformation.find(trans_type) != -1:
            return trans_type
    return "N"

def transform(msg: str, transformation: str, encryption_string: str) -> str:
    """Parses the operation strings given, returns a new string with the given operations applied

    Args:
        msg (str): The message to be transformed
        transformation (str): The given operations to be applied to the msg
        encryption_string (str): E for encryption, D for decryption

    Returns:
        (str): The new message with the given operations applied
    """
    
    split_transformations = transformation.split(";")
    decrypt = False if encryption_string == "E" else True
    direction = -1 if decrypt else 1 # need to decrypt in reverse
    for trans in split_transformations[::direction]:
        trans_type = find_transformation_type(trans)
        if trans_type == "S":
            msg = shift(msg, trans, decrypt)
        elif trans_type == "R":
            msg = rotate(msg, trans, decrypt)
        elif trans_type == "D":
            msg = duplicate(msg, trans, decrypt)
        elif trans_type == "T":
            msg = trade(msg, trans)
        
    return msg

def main() -> None:
    """
    The main program is responsible for getting the input details from user and writing the output file with the results
    of encrypting or decrypting the message file applying the transformations from the operation file.
    :return: None
    """
    
    message_file = input("Please provide a file to read messages from\n")
    operations_file = input("Please provide a file to read operations from\n")
    output_file = input("Please provide a file for output\n")
    encrypt_decrypt = input("(E)ncrypt or (D)ecrypt?\n")
    
    print("Generating output ...")
    with open(message_file) as messages, open(operations_file) as operations, open(output_file, 'w') as output:
        message_line = messages.readline().replace("\n", "")
        operation_line = operations.readline().replace("\n", "")
        while message_line != "" and operation_line != "":
            result = transform(message_line, operation_line, encrypt_decrypt)
            print(result)
            output.writelines([result + "\n"])
            message_line = messages.readline().replace("\n", "")
            operation_line = operations.readline().replace("\n", "")


if __name__ == '__main__':
    main()
