## Analysis 
 i: A stack is going to work with the collected data upwards, basiclly meaning it will focus on one leg of the tree at a time. A queue is going to work with the data downwards, basicaly meaning it will focus on whats next and go through every branch of the leg at the same time, not focusing on just one.

 ii: The total number of search states is not affected by the choice of a stack or queue because they both accomplish the same thing of finding every branch of the search tree.

 iii: Using one storage structure over the other result in finding the most optimized soultion quicker, but overall on average they will both find all the solutions at the same time. I say one might find the optimized solution quicker becasue a stack searches one leg at a time and there is a chance that on the first few legs it checks, the most optimal might be one of those. However, this wont happen every time.

 iv: If you are lucky and you use a stack, there is a chance that the first solution could be found on the first "leg" of the search. However, none of them Guarentee the firsty found solution will be the shortest path.

 v: A queue will have more memory use on average compared to a stack becasue it stores many simultaneous states rather than just one branch of states.

 vi: The search algorithm I believe is going to be O(2^n) where n is the number of paths explored on the board. I say it is O(n^2) because it has to check every single combination, which doubles every time you add an element to the grid. The thing thats tricky about this question is that it depends on the size of the board, how many moves the node can go(up,left,down,right), and how many open spaces there are. 