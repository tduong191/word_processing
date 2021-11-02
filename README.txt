NAME:
=====
	Trang Duong

Programs Files:
===============
    Part 1: Map151.java 
    A program that stores the unique words and their occurrences in an arrayList when given a txt file.

    Part 2: Probe.java 
    A program that stores the unique words and their occurrences in an array using linear probing algorithm when given a txt file.

    Part 3: SepChains.java
    A program that stores the unique words and their occurrences in an array using seperate chaining algorithm when given a txt file.

    Part 4: Hmap.java:
    A program that stores the unique words and their occurrences in a hashmap when given a txt file.

    Part 5: Map151Interface.java
    An interface for Map151. 

    Part 6: MyReader.java
    An object that reads a txt file, store each word in an ArrayList, and return the next word of the file when the nextWord() function is called.
	
How to Compile:
===============
    Part 1: Compile the Map151.java
       javac Map151.java

    Part 2: Compile the Probe.java
       javac Probe.java

    Part 3: Compile the SepChains.java
        javac SepChains.java

    Part 4: Compile the Hmap.java
       javac Hmap.java

       
How to Run:
===========
    Part 1: Run Map151.java. It will return the time it takes to read the file, count the number of word's occurrences, and store the data of word and occurrences.
       java Map151 > type in the filename


    Part 2: Run Probe.java. It will return the time it takes to read the file, count the number of word's occurrences, and store the data of word and occurrences.
       java Probe > type in the filename

    Part 3: Run SepChains.java. It will return the time it takes to read the file, count the number of word's occurrences, and store the data of word and occurrences.
        java SepChains > type in the filename

    Part 4: Run Hmap.java. It will return the time it takes to read the file, count the number of word's occurrences, and store the data of word and occurrences.
       java Hmap > type in the filename


Time Analysis: 
===========
    1. Average time and standard deviation of the time that each algorithm takes to read janeoneword.txt
            Map	        SepChain	Probe	    HashMap
    average	25273.22222	1794.777778	490.8888889	596.5555556
    std dev	1034.730735	260.8671969	57.88446347	86.15989657

    2. Analysis
    From observing the time that it takes each program to run, I believe that Map is the slowest because it uses 
    ArrayList to store, and whenever it needs to know which word has already been added (i.e look up a word), 
    it has to search the entire ArrayList, which is O(n) time. 

    I would expect SepChain to be the second slowest since it uses seperate chaining to deal with collisions and that takes O(n) in the
    worst case that all the keys are hashed to the same index. Since SepChain uses 2 different data structures to store data, it will be 
    slower when trying to add or retrieve data.

    HashMap would be the second fastest because it uses double hashing for collisions and that takes O(logn) for
    the worst case. Probe would be the fastest because it uses Linear Probing for collisions would take O(n) for the worst case, and 
    it is faster than HashMap because it uses only one hashing function while HashMap would use two.



Reflection:
===========
    I appreciate the creativity of this assignmnet. I like designing different approaches for the nextWord() method and counting the number of occurrences.
    I spent a lot of time on the infinite loop in Linear Probing algorithm, which I finally found out that it was because of not enough space, not because of the big file. 
    I'm still not sure about how the Seperate Chain program add key value pairs to the array.


I Worked With:
==============
I asked the TA when I stucked in the infinite loop. 

Approximate Hours worked:
=========================
I think about 15 - 20 hours.

Special Instructions to the grader:
===================================
As needed -- but generally your code should need no special instructions

Known Bugs or Limitations:
==========================
No bugs.

Other comments:
===============
It was fun, but a bit more time consuming than I had expected.