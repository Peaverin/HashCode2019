# Google Hashcode 2019 Qualification Round
## Solution by team: Zarkram ([@Peaverin](https://github.com/Peaverin/), [@nauQs](https://github.com/nauQs), Iván Correa)
The code for the qualification round was originally written in python but we ported it to java as it's much faster.
### Algorithm: Greedy | Explanation:
For each file:
1. Read file and load two lists: vertical photos and horizontal photos.
2. Create slides from vertical photos: First we order the list of vertical photos by number of tags, then we create slides using the first and the last element from the list until there are no more pairs in the list. This way all the slides made from vertical photos will have an average number of tags. This way the average interest between slides would be optimized as we don't know the distribution of tags over the photos.
3. Mix all the slides (horizontal photos + slides made of two vertical photos) in one list. We first add the vertical slides, then the horizontal ones. At first we though of alternating between 1 vertical and 1 horizontal but that gave us less points.
4. Greedy Approach: We compare each slide from the list with the last AMOUNT slides of the list, where AMOUNT is a variable parameter. When the size of the list is less than AMOUNT, we compare with every element.
Time of algorithm increases in a linear way depending in AMOUNT parameter.

A folder must be created before running the algorithm, with name "TimesLog". It has to be located in the same folder as the src folder. Otherwise you can erase the lines were the program creates and writes to a file in that folder.

Some time and score tables using the same computer:
