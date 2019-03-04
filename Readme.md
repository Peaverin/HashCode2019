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

Some time and score tables using the same computer playing with the AMOUNT parameter:
AMOUNT = 10
a_example: 3ms              | Score: 1<br/>
b_lovely_landscapes: 3218ms | Score: 183<br/>
c_memorable_moments: 20ms   | Score: 723<br/>
d_pet_pictures: 982ms       | Score: 319564<br/>
e_shiny_selfies: 1572ms     | Score: 214493<br/>
Total time: 5795ms          | Total Score: 534964<br/>
<br/>
AMOUNT = 20<br/>
a_example: 3ms              | Score: 1<br/>
b_lovely_landscapes: 3899ms | Score: 351<br/>
c_memorable_moments: 34ms   | Score: 908<br/>
d_pet_pictures: 1531ms      | Score: 344033<br/>
e_shiny_selfies: 2574ms     | Score: 239050<br/>
Total time: 8041ms          | Total Score: 584343<br/>
<br/>
AMOUNT = 30<br/>
a_example: 4ms              | Score: 1<br/>
b_lovely_landscapes: 4658ms | Score: 552<br/>
c_memorable_moments: 71ms   | Score: 1024<br/>
d_pet_pictures: 1992ms      | Score: 356718<br/>
e_shiny_selfies: 3786ms     | Score: 252560<br/>
Total time: 10511ms         | Total Score: 610855<br/>
<br/>
AMOUNT = 40<br/>
a_example: 3ms              | Score: 1<br/>
b_lovely_landscapes: 5436ms | Score: 714<br/>
c_memorable_moments: 74ms   | Score: 1077<br/>
d_pet_pictures: 2552ms      | Score: 364980<br/>
e_shiny_selfies: 4564ms     | Score: 261732<br/>
Total time: 12629ms         | Total Score: 628504<br/>
<br/>
AMOUNT = 50<br/>
a_example: 3ms              | Score: 1<br/>
b_lovely_landscapes: 6179ms | Score: 912<br/>
c_memorable_moments: 73ms   | Score: 1124<br/>
d_pet_pictures: 2917ms      | Score: 371138<br/>
e_shiny_selfies: 5075ms     | Score: 268366<br/>
Total time: 14247ms         | Total Score: 641541<br/>
<br/>
AMOUNT = 100<br/>
a_example: 3ms              | Score: 1<br/>
b_lovely_landscapes: 9746ms | Score: 1800<br/>
c_memorable_moments: 98ms   | Score: 1264<br/>
d_pet_pictures: 5372ms      | Score: 387673<br/>
e_shiny_selfies: 8869ms     | Score: 288919<br/>
Total time: 24088ms         | Total Score: 679657<br/>
<br/>
AMOUNT = 500
a_example: 4ms               | Score: 1<br/>
b_lovely_landscapes: 38753ms | Score: 8769<br/>
c_memorable_moments: 214ms   | Score: 1427<br/>
d_pet_pictures: 25024ms      | Score: 410056<br/>
e_shiny_selfies: 44850ms     | Score: 332166<br/>
Total time: 108845ms         | Total Score: 752419<br/>
<br/>
AMOUNT = 1000<br/>
a_example: 3ms               | Score: 1<br/>
b_lovely_landscapes: 79490ms | Score: 16827<br/>
c_memorable_moments: 249ms   | Score: 1423<br/>
d_pet_pictures: 50740ms      | Score: 412972<br/>
e_shiny_selfies: 91176ms     | Score: 348804<br/>
Total time: 221658ms         | Total Score: 780027<br/>

