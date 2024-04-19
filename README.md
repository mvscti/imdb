# imdb
IMDBs are databases that do not work with physical file management, keeping all main memory data and indexes. Therefore, they are simpler and use is restricted to computer's RAM capacity, but what caused queries to be processed very fast. This project simulates an IMDB and the objective is to import an SQL file. The project uses separate chaining hashing to handle collisions.


After compiling and generating a JAR file as follows:
```
$ jar cvf IMDB.jar src
```
The application can be executed in two ways. The first one is using the GUI. To do this, simply execute the command in the terminal:
```
$ java –jar IMDB.jar
```
Or if you prefer, you can use the IMBD without GUI:
```
$ java –jar IMDB.jar “path/to/sql-file”
```

## Note
I recomend choosing one of the [dump sql files](https://www.postgresql.org/ftp/projects/pgFoundry/dbsamples/) to perfom the tests.