import sys

sys.argv # this is a list
if len(sys.argv) != 0:
    for arg in sys.argv:
        print(arg)