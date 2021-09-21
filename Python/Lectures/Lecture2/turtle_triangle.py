import turtle

def triangle(size):
    for _ in range(3):
        turtle.forward(size)
        turtle.left(120)
        
def draw_triangles(count):
    for _ in range(count):
        triangle(100)
        turtle.penup()
        turtle.right(90)
        turtle.forward(50)
        turtle.pendown()
        
def setup():
    turtle.title("Triangles")
    turtle.speed(0)
    
def main():
    setup()
    draw_triangles(3)
    turtle.mainloop()
    
if __name__ == "__main__":
    main()