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

These are ranked in order of time to bound on cooling, from fast to slow. With an original temperature of 100 and a limit of 0.0005, 1 decreases past the limit in 100 iterations, 2 decreases past the limit in 1282055 iterations, and 3 decreases past the limit in 199999 iterations. Since the optimality of simulated annealing is only guaranteed as T decreases infinitely slowly, the slower the cooling the better. Indeed, even with medium length solutions such as 5 city problems, already when using Schedule 1 some non-optimal solutions were returned, with the other two providing results with a strictly lower cost. 3 takes the most iterations to complete, but decreases past the high values very rapidly, with t = 25 in only 4 iterations. 2 is the slowest to cool (which allows exploration in the beginning stages), so that is the schedule recommended.

The algorithm assumes that all Cities in the input have direct paths between them; that is, the input forms a complete graph. The solution is thus an optimal path that visits every City, ending at the same location at which it started.

To run, execute the following commands in the src directory:
```
javac *.java
java TravellingSalesman ../example.tsp [schedule-number]
```

See [Wikipedia](https://en.wikipedia.org/wiki/Simulated_annealing) for more information on Simulated Annealing, especially the section on [the annealing schedule](https://en.wikipedia.org/wiki/Simulated_annealing#The_annealing_schedule).
