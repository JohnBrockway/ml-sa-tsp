# ml-sa-tsp
A Java implementation of Simulated Annealing to solve Travelling Salesman-esque problems. Takes an argument specifying a file path to the input problem, in the following form:
```
# of Cities
CityName x-coordinate y-coordinate
CityName2 x-coordinate y-coordinate
etc...
```
See the provided [example.tsp](example.tsp) for a more concrete example.

Also takes an argument specifying which annealing schedule to use, from 1 to 3:
* Schedule 1: T decreases in a linear fashion by 1 each iteration
* Schedule 2: T decreases by a factor of .01% each iteration
* Schedule 3: T is equal to 100 (the original temperature) divided by the current iteration number

The algorithm assumes that all Cities in the input have direct paths between them; that is, the input forms a complete graph. The solution is thus an optimal path that visits every City, ending at the same location at which it started.

To run, execute the following commands in the src directory:
```
javac *.java
java TravellingSalesman ../example.tsp [schedule-number]
```

See [Wikipedia](https://en.wikipedia.org/wiki/Simulated_annealing) for more information on Simulated Annealing, especially the section on [the annealing schedule](https://en.wikipedia.org/wiki/Simulated_annealing#The_annealing_schedule).
