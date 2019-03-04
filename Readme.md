# Google Hashcode 2019 Qualification Round
## Solution by team: Zarkram (@Peaverin, @nauquin, 

### Algorithm: Greedy | Explanation:
For each file:
1. Read file and load two lists: vertical photos and horizontal photos.
2. Create slides from vertical photos: First we order the list of vertical photos by number of tags, then we create slides using the first and the last element from the list until there are no more pairs in the list. This way all the slides made from vertical photos will have an average number of tags. This way the average interest between slides would be optimized as we don't know the distribution of tags over the photos.
3. Mix all the slides (horizontal photos + slides made of two vertical photos) in one list. We first add the vertical slides, then the horizontal ones. At first we though of alternating between 1 vertical and 1 horizontal but that gave us less points.
4. Greedy Approach: We compare each slide from the list with the last AMOUNT slides of the list, where AMOUNT is a variable parameter. When the size of the list is less than AMOUNT, we compare with every element.
Time of algorithm increases in a linear way depending in AMOUNT parameter.

