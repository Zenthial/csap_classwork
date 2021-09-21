""" RunConfiguration Converter
Converts a folder of run configuration .xml's into a folder of .cmd scripts that call py with the appropriate arguments

Arguments:
    Requires two command line arguments, the path to the xmls, and the path for the cmds
    Optional third argument for $PROJECT_DIR$ path, defaults to .. 

Author: Alex Pickering
"""

import os
import sys
from xml.etree import ElementTree as ET

def main() -> None:
    """ tis main, does the things
    """
    # parse input
    if len(sys.argv) < 3 or len(sys.argv) > 4:
        print("incorrect args")
        exit()
    
    xml_dir = sys.argv[1].replace("\\", "/")
    cmd_dir = sys.argv[2].replace("\\", "/")

    if len(sys.argv) == 4:
        proj_dir = sys.argv[3].replace("\\", "/")
    else:
        proj_dir = ".."
    
    # take off trailing /
    if xml_dir[-1] == "/":
        xml_dir = xml_dir[:-1]
    
    if cmd_dir[-1] == "/":
        cmd_dir = cmd_dir[:-1]
    
    if proj_dir[-1] == "/":
        proj_dir = proj_dir[:-1]
    
    # each file
    for f in os.scandir(xml_dir):
        cmd_name = f.name[:-3] + "cmd"
        print(f.name, "->", cmd_name) # debug but also tells you its doing stuff

        # gimme contents
        with open(cmd_dir + "/" + cmd_name, "w") as cmd:
            tree = ET.parse(xml_dir + "/" + f.name)
            root = tree.getroot()

            # py [-u] script [args] [< input]
            command = "python3 "

            # -u
            if root.find(".//*[@name='PYTHONUNBUFFERED']") is not None: # if it's specified assume 1
                command += "-u "
            
            # script
            command += "\"" + proj_dir + root.find(".//*[@name='SCRIPT_NAME']").get("value")[13:] + "\" "

            # args
            command += root.find(".//*[@name='PARAMETERS']").get("value") + " "

            # input
            command = command.strip()
            input = root.find(".//*[@name='INPUT_FILE']").get("value")
            if input != "":
                command += " < \"" + proj_dir + input[13:] + "\""
            
            cmd.write(command)

if __name__ == "__main__":
    main()