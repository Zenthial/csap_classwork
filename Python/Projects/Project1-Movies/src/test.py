import turtle

def drawSquares(depth,length):
    if depth < 0:
        pass
    else:
        turtle.forward(length)
        turtle.left(90)
        drawSquares(depth-1,length/3)

        turtle.forward(length)
        turtle.left(90)


        turtle.forward(length)
        turtle.left(90)

        turtle.forward(length)
        turtle.left(90)

maxSize = 100
depth = int(input("Enter a depth: "))
turtle.speed(0)
drawSquares(depth,maxSize)
input("Hit Enter to close.")
