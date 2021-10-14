# Exceptions
Exception class Hierachy in Java

    Throwable
        Exception
            RuntimeException
                NullPointer
                NoSuchElement
                IndexOutOfBounds
                Arithmetic
                ClassCast
                IllegalArgument
                IllegalState
            ClassNotFoundException
            IOException
            CloneNotSupportedException
        Error

**Scanner makes IO Exceptions RuntimeExceptions**

Handling Exceptions

    CheckedMain
        Adding "throws (ExceptionName)" to the method header, done for anything except RuntimeExceptions
    TryCatch
        Adding a try catch block to catch the exception and handle it as you want

Exceptions propogate upwards

# Input / Output
Import java.io

```java
PrintWriter output = new PrintWriter(new FileWriter(filename)); // allows you to write to a file
BufferedReader input = new BufferedReader(new FileReader(filename)); // allows you to read from a file
```

java.IO classes expose exceptions, while Scanner's are hidden

A stream in the I/O sense:

    Limited Operations
        Must read things in order
    Polymorphic
        Text file
        User input from console
        Output to console
        Network I/O
        Connections between programs ("pipes")
        Embeded devices that send data periodically

Readers and Writers vs Input and Output Streams

    byte vs char (vs int)

Reader
    These classes decorate something
        FileReader
        FilterReader
    Basic functionality
        StringReader
        InputStreamReader
    Extends Readers
        BufferedReader
        LineNumberReader