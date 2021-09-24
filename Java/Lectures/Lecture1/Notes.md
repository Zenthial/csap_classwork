```java
// scanner magic
for (Scanner input = new Scanner(new File("file.txt")); input.hasNextLine()) {
    System.out.println(input.nextLine());
}

// or
Scanner input = new Scanner(new File("file.txt"));
while (input.hasNextLine()) {
    System.out.println(input.nextLine());
}
```