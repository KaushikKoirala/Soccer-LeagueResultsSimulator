# Soccer League Results Simulator
Program that can print to console various possible results, statistics, and simulations, given a number of games that are played in that soccer league in one season. 


## Premise Behind the Program
In soccer leagues throughout the world, rankings are computed by assigning 3 points for every win a team in that league gets, 1 point for every draw, and 0 points for every loss. Given that this is the case, it can lead to some very interesting results. For example, in  a 38 game season, a team with 12 wins, 6 draws, and 20 losses will have finished level on points in the standings with a team with 14 wins, 0 draws, and 24 losses as they both would have 42 points.
This, along with the fact that not all leagues play the same number of games, makes it interesting to see various potential data about a league and the number of points that can be attained in those games. 

## Explaining the Menu Options
### Menu Option One
Menu Option One can enumerate to the user, given  a number of points, the number of ways (via various amounts of wins, draws, or losses) a team can achieve that amount of points in a given season. For example, if the user wants to find out the number of ways a team can obtain 36 points in a 38 game season, the menu option will print something like this to the console: 
```
There are 13 ways to attain 36 points.
The ways are: 

0 WINS 36 DRAWS 2 LOSSES
1 WINS 33 DRAWS 4 LOSSES
2 WINS 30 DRAWS 6 LOSSES
3 WINS 27 DRAWS 8 LOSSES
4 WINS 24 DRAWS 10 LOSSES
5 WINS 21 DRAWS 12 LOSSES
6 WINS 18 DRAWS 14 LOSSES
7 WINS 15 DRAWS 16 LOSSES
8 WINS 12 DRAWS 18 LOSSES
9 WINS 9 DRAWS 20 LOSSES
10 WINS 6 DRAWS 22 LOSSES
11 WINS 3 DRAWS 24 LOSSES
12 WINS 0 DRAWS 26 LOSSES
```

### Menu Option Two
Menu Option Two lists in sorted order to the user  given a number of games, which points totals are most common based on the number of different ways it can be attained. For example, in a league with 30 games (like the Russian Premier League), the most common points total is 30 points, with 11 different ways to attain those points. In order to find out what those ways are, the user would have to go back and use Menu Option One. 

### Menu Option Three
Menu Option Three lists the median, mean, and standard deviation of all possible point totals for a team given the number of games. 

### Menu Option Four
Menu Option Four allows the user to simulate  a randomized table (or league rankings) based on the pre-input number of games, and the prompted number of teams. This is done by dividing a list of all potential points results (based on the number of games) and dividing those points totals into subgroups based on the number of teams. The program then chooses randomly within the subgroup, with a heavier bias for the points totals that can be achieved in more ways.

### Menu Option Five
Menu Option Five allows the user to set a different number of games than the one they had set prior. For example, if the user wants to switch from a 38-game season to a 34-game season, they would select option five. 

## Instructions on use 
Download all three Java files from the *src* folder, and ensure that they are in the same directory. Compile *ProgramRunner.java* with a *Java* compiler and run the program. The rest of the instructions and guide to following the program should be printed to the console. 
