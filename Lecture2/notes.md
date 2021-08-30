# Lecture 2 Notes
**Execution Diagrams**

## Example 1:
    Based off of turtle_triangle.py
    * main()
        + setup
        + draw_trangles(count = 2)
          - draw_triangle(size = 100)
          - draw_triangle(size = 100)

## Debugging
    Use breakpoints
    Step into function calls you think aren't working
    Step over them if you know your problem doesn't exist there
    Place a breakpoint where you want to start debugging

## Recursion
```py
def xs_not_recursion(size: int):
    print('X'*size)

def xs_rec(size: int):
    # recursively print a line of X characters
    if size == 0: # basecase
        print()
    else:
        print("X", ends="")
        xs_rec(size - 1)

def fibo(n):
    if n < 2:
        result = n
    else:
        result = fibo(n-2) + fibo(n-1)
        
    return result
```