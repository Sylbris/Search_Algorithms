# Search_Algorithms
This is a course in Ariel University that focus on all pathfinding algorithms, including A* , IDA, DFBNB

# Exercise
I have implemented a search engine, that supports multiple search algorithms.
The goal is to solve the board game "marble color shuffle".

# Game rules:

Your given an input board:

![alt text](https://i.ibb.co/QYSxJH4/Screenshot-4.png)


Goal board:

![alt text](https://i.ibb.co/Srw9G9S/Screenshot-5.png)

Then the input includes which search algorithm to use,
Wether to print the open list at each iteration.

each turn your allowed, to move one of the marbles in any empty slot,
your allowed to move left, right , up or down (if that slot is empty).
each moving of a marble has a cost depending its color.

Note: Your not allowed to perform an action and its inverse,
so if you move a marble to the left, your not allowed to move it right
the turn after.

Output:

The path to the goal state,
Cost to the goal state,
Number of nodes created.

# Algorithms
1. BFS
2. DFID
3. A*
4. IDA*
5. DfBnB


