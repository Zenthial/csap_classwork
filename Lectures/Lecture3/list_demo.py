"""
CSAPX Week 3: Searching and Sorting

Author: Sean Strout @ RIT CS

A demonstration of some of the operations you can perform with lists and
tuples.

To run:
    $ python3 list_demo.py arg1 arg2 ...
"""

import sys    # argv

def main():
    """
    main function demonstrates operations of lists and tuples.
    """
    print('sys.argv:', sys.argv)

    # homogeneous list
    print('\nlst_a = [1,2,3,4]')
    lst_a = [1,2,3,4]

    # heterogeneous list
    print("\nlst_b = [1, 2.3, 'a', min]")
    lst_b = [1, 2.3, 'a', min]
    print('lst_b:', lst_b)

    # list element assignment
    print('\nlst_a[0] = 9')
    lst_a[0] = 9

    print('\nlst_a:', lst_a)
    print('len(lst_a):', len(lst_a))

    # list pop
    print('\nlst_a.pop(0):', lst_a.pop(0))
    print('lst_a:', lst_a)

    # list insert
    print('\nlst_a.insert(1, 9)')
    lst_a.insert(1, 9)
    print('lst_a:', lst_a)

    # list append
    print('\nlst_a.append(10)')
    lst_a.append(10)
    print('lst_a:', lst_a)

    # list index
    print('\nlst_a.index(10):', lst_a.index(10))

    print('\nlst_b = [21,22,23]')
    lst_b = [21,22,23]

    # list extend
    print('\nlst_a.extend(lst_b)')
    lst_a.extend(lst_b)
    print('lst_a:', lst_a)
    print('lst_b:', lst_b)

    # list concatenation
    print('\nlst_b + lst_a:', lst_b + lst_a)
    print('lst_a:', lst_a)
    print('lst_b:', lst_b)

    # list reverse
    print('\nlst_a.reverse()')
    lst_a.reverse()
    print('lst_a:', lst_a)

    # list sort
    print('\nlst_a.sort()')
    lst_a.sort()
    print('lst_a:', lst_a)

    # tuple
    print('\ntup_a = (1,2,3)')
    tup_a = (1,2,3)   # or just: tup_a = 1,2,3
    print('tup_a:', tup_a)

    # tuple element access
    print('\ntup_a[0]:', tup_a[0])

    # tuple element assignment is illegal
    print('\ntup_a[0] = 9')
    try:
        tup_a[0] = 9
    except TypeError as te:
        print('TypeError:', te)

    # convert tuple to list
    print('\nlist(tup_a):', list(tup_a))
    print('tup_a:', tup_a)

    # convert list to tuple
    print('\ntuple(lst_a):', tuple(lst_a))
    print('lst_a:', lst_a)

if __name__ == '__main__':
    main()
