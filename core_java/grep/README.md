# Introduction

This project is a command-line tool for searching files and directories using regular expressions,
similar to the Unix `grep` utility. It also features an implementation that leverages Lambda
Expressions and Streams to optimize memory usage. Additionally, the application is dockerized for
simplified deployment and usage. The project explores OOP and core Java concepts and utilizes
Java's `Pattern` class for regex matching.

# Quick Start

### Java command

```bash
# Package the application into jar
mvn clean package

# Run the application
java -jar target/grep-1.0-SNAPSHOT.jar <regular-expression> <root-search-directory> <output-file>
```

### Docker

*In the instructions below, the __root search directory__ is set to the `data/` folder, and the
__output file__ is set to `log/grep.out`.*

```bash
# Package application into jar
mvn clean package

# Build docker image
docker build -t <image-name>

# Create and execute container
docker run --rm -v `pwd`/data:/data -v `pwd`/log:/log <image-name> <regular-expression> /data /log/grep.out
```

# Implemenation

The app iterates through all files in a root search directory, reads each line, checks if the line
contains a specified regular expression, and writes matching lines to an output.

```python
for file in listFiles(root-search-directory):
    for line in readLines(file):
        if line.contains(regular-expression):
            writeLineToOutput()
```

### Stream implementation and performance Issues

With the non-stream approach above, all lines from all files are loaded into lists during the search
and matching process, which results in high memory usage. To address this, an alternative
implementation using streams has been made to process lines more efficiently.

A [`JavaGrepLambdaImp`](src/main/java/ca/jrvs/apps/grep/JavaGrepLambdaImp.java) class was created,
with the methods `readLinesStream()` and `listFilesStream()` implemented to use and return streams
instead of lists, improving memory efficiency by processing data lazily.

```python
matchedLines = streamFiles(rootPath)
.mapToLines(...)
.filter(...)
.collectToList()

writeToFile(matchedLines)
```

# Test

The application was tested using various regular expressions to verify matching functionality
against [`data/txt/shakespeare.txt`](data/txt/shakespeare.txt).

Tests were also performed on the project's root directory to ensure proper handling of a real-world
directory structure.

The application was further tested with text and binary files, confirming it
processes text files correctly while skipping binary files.

# Deployment

A Dockerfile was created to help deploy the application:

```dockerfile
FROM openjdk:8-alpine
COPY target/grep*.jar /usr/local/app/grep/lib/grep.jar
ENTRYPOINT ["java", "-jar", "/usr/local/app/grep/lib/grep.jar"]
```

This Dockerfile can be used to build a Docker image. Containers can then be started from that image
using the docker run command provided in the [Quick Start](#quick-start) section.

# Improvement

#### 1) Simplify running the docker container

To simplify running the Docker container, we could add a `CMD` instruction to
the [`Dockerfile`](Dockerfile) with default arguments. Additionally, we could create a script that
wraps the docker run command, requiring only the Java application's arguments.

#### 2) Simplify access to `Stream` implementation

Currently, the stream implementation of the app, which uses streams for lazy operations, is
defined in a separate class with its own main method, making it difficult to run. We could
refactor it to remove the main method and have a centralized main method that accepts an extra
parameter to determine whether to use the stream version or the non-stream version.

#### 3) Ignore hidden files

The application currently does not ignore hidden files. We could add a parameter to allow it to
ignore hidden files during processing.