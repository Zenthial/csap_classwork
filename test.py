cost_dict = {"purple":50, "light_blue":50,
             "maroon":100, "orange":100,
             "red":150, "yellow":150,
             "green":200, "dark blue":200}
size_dict = {"purple":2, "light_blue":3,
             "maroon":3, "orange":3,
             "red":3, "yellow":3,
             "green":3, "dark blue":2}

color = input("Which color block will you be building on? ")
money = int(input("How much money do you have to spend? "))

cost = cost_dict[ color ]
size = size_dict[ color ]

print("There are",size,"properties and each house costs",cost)
buildings = money // cost

small_build = buildings // size
large_build = small_build + 1
large_group = buildings % size
small_group = size - large_group

numbers = ['none','one','two','three','four','five','six',
           'seven','eight','nine','ten','eleven','twelve',
           'thirteen','fourteen','fifteen']


if buildings == 0:
    print("You cannot afford even one house.")

if small_build >= 5:
    print("You can build",numbers[size * 5],"house(s) --",
      numbers[small_group],"will have a hotel.")
elif large_build >= 5:
    print("You can build",numbers[buildings],"house(s) --",
      numbers[small_group],"will have",numbers[small_build],"and",
      numbers[large_group],"will have a hotel.")
else :
    print("You can build",numbers[buildings],"house(s) --",
      numbers[small_group],"will have",numbers[small_build],"and",
      numbers[large_group],"will have",numbers[large_build])

"""
print("You can build",numbers[buildings],"house(s) --",
      numbers[small_group],"will have",numbers[small_build],"and",
      numbers[large_group],"will have",numbers[large_build])
"""