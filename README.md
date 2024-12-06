****************
* Circuit Project
* CS 221 - 002 with Mason Vail
* Due: 12/6/24
* Brayden Xenos
**************** 


## OVERVIEW:

 This program reads in a circuit and uses a brute force search algorithm to not only find all the possible solutions of the circuit, but also the most optimal one(s).


## INCLUDED FILES:

* CircuitTracer.java - driver class
* CircuitBoard.java - represents the circuit
* Storage.java - container for TraceState objects
* TraceState.java - represents paths
* OccupiedPositionException.java - handles occupied positions
* InvalidFileFormatException.java - handles invalid file formats
* README - overview of the program


## COMPILING AND RUNNING:

 From the directory containing all necessary files, compile all java classes with the command:
 $ javac *.java

 Run the compiled class file with the command:
 $ java CircuitTracer -s|-q -c|-g <inputfile>
 
 If an invalid input is detected, a usage message will appear for more information

## ANALYSIS:

 i: A stack is going to work with the collected data upwards, basiclly meaning it will focus on one leg of the tree at a time and go through every scenario in an "upwards" direction. A queue is going to work with the data downwards, basicaly meaning it will focus on whats next and go through every branch of the leg at the same time, not focusing on just one.

 ii: The total number of search states is not affected by the choice of a stack or queue because they both accomplish the same thing of finding every branch of the search tree.

 iii: Using one storage structure over the other results in finding the most optimized soultion quicker, but overall on average they will both find all the solutions at the same time. I say one might find the optimized solution quicker becasue a stack searches one leg at a time and there is a chance that on the first few legs it checks, the most optimal might be one of those. However, this wont happen every time.

 iv: If you are lucky and you use a stack, there is a chance that the first solution could be found on the first "leg" of the search. However, none of them Guarentee the first found solution will be the shortest path.

 v: A queue will have more memory use on average compared to a stack becasue it stores many simultaneous states rather than just one branch of states.

 vi: The search algorithm I believe is going to be O(4^n) where n is the number of paths explored on the board. I say it is O(4^n) because it has to check every single combination, which grows exponentially with the number of steps. The complexity depends on the size of the board, the number of possible moves (up, down, left, right), and the number of open spaces.

## PROGRAM DESIGN AND IMPORTANT CONCEPTS:

 The main functionality of this program is to do a brute force searching algorithm on a 2D array that contains letters that represent positions of a circuit board: 
  - OPEN = 'O'   ~ capital 'o', an open position
  - CLOSED = 'X' ~ a blocked position
  - TRACE = 'T'  ~ part of the trace connecting 1 to 2
  - START = '1'  ~ the starting component
  - END = '2'    ~ the ending component

 The point of a brute force searching algorithm is to find ALL possible answers to something, and in doing so we can find all possible solutions to the circuit board and derive the most optimal from those solutions.

 The way I went about finding all the possible solutions was using either a stack or a queue depending on what the user selects. Both of those options work to solve all the solutions but they just go about it in different ways. Look at the Analysis section for more informaion on using a stack vs. a queue. 

 I organized my document with javadocs, strategicly placed whitespace, and comments throughout that track my thought process and explain what the code is doing. I did this in hopes of future readers not having to read through complex code to understand what is going on, instead they can refer to my comments.

 All of the classes included play a role in the functionality of the program. The `Storage` class acts as a container for TraceState objects using either a stack or a queue, providing methods that are used in other classes for data manipulation. The `TraceState` class represents potential paths through the circuitboard, and methods that are used for a search in CircuitTracer. The `CircuitBoard` class reads in a file and converts the file to a 2D array of the contents. If there are any issues, an exception will be thrown outlining what was wrong with the inputed file. The `CircuitTracer` driver class takes in the args that the user inputs, initializes the correct components, and starts the brute force search using the searching algorithm with either a stack or queue. The `OccupiedPositionException` and `OccupiedPositionException` classes are resposible for handling any exceptions that occur during the execution of the program, such as when a position on the circuit board is already occupied or when the input file format is invalid.

 I was not responsible for choosing the classes, but I was responsible for ensuring an efficient interaction between the different classes. The `CircuitTracer` class coordinates the overall process, using the `CircuitBoard` to read and represent the circuit, the `Storage` class to manage the search states, and the `TraceState` class to represent individual paths. This design allows for flexibility in choosing the search strategy (stack or queue) and ensures that each class has a well-defined role. 

 One improvement that I could have made is adding a GUI which would provide an easy to use and easy to understand functionality for the user. I am also aware that the brute force search algorithm used in this is very inefficent, so there may be a better way of approaching the search for future versions.

## TESTING:

 The main way I tested my program was with the tester included in the assignments zip. These were super helpful for debugging and finding all the little errors in the program. The main bugs that it helped me notice were having proper error handling for all situations. Becasue of these tests, and my program eventually passing all the tests, I am confident that it can take in any bad input. As of now, I am not aware of any bugs that remain in my program. 

## DISCUSSION:

 This project was definitely one of the hardest to debug becasue of all the iterations it had to go through, and tracking those errors were challenging at times. One example of this was when the tester kept failing all the "CircuitTracer Valid Input File Tests". I was really struggling to understand why this was happening becasue all the storing of my data was correct, but after a few hours of going over my code I found that I used the wrong variable name when checking each open adjacent position in the while loop. I had originally just copied and pasted the one from above which used the `board` variable, but what I failed to do at first was to change that to the variable which helpd the current trace state `currentTraceState`. I found this bug by stepping thorugh the debugger and finding that it threw an error as soon as it checked an adjacent position so I knew it was something with that block. After finding this, however, it was very rewarding to see all my tests pass. Now I know to never blindly copy and paste a block of code. 

 Another challenge I ran into was simply conceptualizing what this code was going to do and how I was going to do it. I feel like a brute force searching algorithm sounds a lot harder than it actually was. What really helped me understand this though was drawing everything out like it said in the project description. Drawing it out was really my "Ah ha" moment becasue it helped me visualize the diffence between sorting with a stack vs with a queue.

 Overall I felt much better about this one than some of the others this semester. I am super happy by the result and I think this is definitely one of the cooler ones we did in this course. I feel like my knowledge of java, testing, code efficency, object oriented programming, and just coding in general has grown so much this semester, and it really shows with this project. I am super excited to take the skills I have learned from every assignment in this class, including this one, and use that knowledge to build bigger and more complex programs!
