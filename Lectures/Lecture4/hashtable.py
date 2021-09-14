"""
CSAPX Week 4: Hashing

An implementation of a hash table that uses open addressing to resolve
collisions.

This version does not implement rehashing and when the size reaches
the capacity an exception will be thrown if a new entry is added.

author: RIT CS
"""

from dataclasses import dataclass
from typing import Any, Hashable, Callable, Optional

@dataclass
class Entry:
    """
    A class used to hold key/value pairs.
    key: hashable key
    value: any value
    """
    key: Hashable
    value: Any

@dataclass
class HashTable:
    """
    The HashTable data structure contains a collection of values
    where each value is located by a hashable key.
    No two values may have the same key, but more than one
    key may have the same value.
    table: is the list holding the hash table
    size: is the number of elements in occupying the hash table
    capacity: the max capacity of the table
    hash_func: the hash function for the key
    """
    table: list[Optional[Entry]]
    size: int
    capacity: int
    hash_func: Callable[[Hashable],int] # a function of (Hashable) -> int

def create_hash_table( \
          hash_function: Callable[[Hashable],int], capacity: int) -> HashTable:
    """
    Create a new empty hash table and return it.
    :param hash_function: the immutable key's hash function
    :param capacity: the capacity of the table
    :return: the new empty hash table
    """
    if capacity < 2:
        capacity = 2
    table = list()
    for i in range(capacity):
        table.append(None)
    hash_table = HashTable(table, 0, capacity, hash_function)
    return hash_table

def hash_table_to_str(hash_table: HashTable) -> str:
    """
    Return a string representation of the hash table.
    :param hash_table: the hash table
    :return: a "stringified" hash table
    """
    result = ''
    for i in range(hash_table.capacity):
        e = hash_table.table[i]
        if e is not None:
            result += str(i) + ': '
            result += entry_to_str(e) + '\n'
        else:
            result += str(i) + ': None' + '\n'
    return result

def entry_to_str(entry: Entry) -> str:
    """
    Return a string representation of an entry.
    :param entry: the entry
    :return: a string containing the key and value
    """
    return '(' + str(entry.key) + ', ' + str(entry.value) + ')'

def keys(hash_table: HashTable) -> list[Hashable]:
    """
    Return the keys in as hash table.
    :param hash_table: the hash table
    :return: a list of keys
    """
    result = list()
    for entry in hash_table.table:
        if entry is not None:
            result.append(entry.key)
    return result

def values(hash_table: HashTable) -> list[Any]:
    """
    Return the values in as hash table.
    :param hash_table: the hash table
    :return: a list of values
    """
    result = list()
    for entry in hash_table.table:
        if entry is not None:
            result.append(entry.value)
    return result

def has(hash_table: HashTable, key: Hashable) -> bool:
    """
    Check if a key exists in a hash table
    :param hash_table: the hash table
    :param key: they key to search for
    :return: whether the key is in the table or not
    """
    index = hash_table.hash_func(key) % hash_table.capacity
    start_index = index
    while hash_table.table[index] is not None and \
          hash_table.table[index].key != key:
        index = (index + 1) % hash_table.capacity
        if index == start_index:
            return False  # make sure not to go in circles
    return hash_table.table[index] != None

def put(hash_table: HashTable, key: Hashable, value: Any) -> Any:
    """
    Put a key/value pair into the table.  If the key doesn't exist
    in the table, we add a new entry to a new location in the table.
    Otherwise we update the value of the existing entry.
    :param hash_table: the hash table
    :param key: the key
    :param value: the value
    :raise Exception if the hash table is full
    :return: if the key already exists, its old value, otherwise None
    """
    index = hash_table.hash_func( key) % hash_table.capacity
    start_index = index
    while hash_table.table[index] is not None and \
          hash_table.table[index].key != key:
        index = (index + 1) % hash_table.capacity
        if index == start_index:
            raise Exception("Hash table is full.")
                                       # make sure not to go in circles
    if hash_table.table[index] is None:  # new entry, return None
        hash_table.table[index] = Entry(key, value)
        hash_table.size += 1
        return None
    else:                 # update existing entry, return old value
        old_value = hash_table.table[index].value
        hash_table.table[index].value = value
        return old_value

def get(hash_table: HashTable, key: Hashable) -> Any:
    """
    Get the value associated with a key in the table
    :param hash_table: the hash table
    :param key: the key
    :raise Exception if the table does not contain the key
    :return: the value
    """
    index = hash_table.hash_func(key) % hash_table.capacity
    start_index = index
    while hash_table.table[index] is not None and hash_table.table[index].key != key:
        index = (index + 1) % hash_table.capacity
        if index == start_index:
            raise Exception("Hash table does not contain key.")
                                       # make sure not to go in circles
    if hash_table.table[index] is None:
        raise Exception("Hash table does not contain key:", key)
    else:
        return hash_table.table[index].value

def non_oo_demo() -> None:
    """Show how to use the HashTable class."""
    h_table = create_hash_table( hash, 20 )
    put( h_table, "speed-of-light", 2.998e8 )
    print( "speed-of-sound in table:", has( h_table, "speed-of-sound" ) )
    print( "speed-of-light in table:", has( h_table, "speed-of-light" ) )
    print( "Speed of light is", get( h_table, "speed-of-light" ), "m/sec" )
    print( hash_table_to_str( h_table ) )

if __name__ == "__main__":
    non_oo_demo()
