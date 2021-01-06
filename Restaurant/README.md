# Restaurant

You have been tasked with implementing a restaurant seating system.
The restaurant seating system should allow tables to be added and removed from the restaurant.
It should also allow parties to be booked so that they can wait for a table to become available.
The system should allow both seated and unseated parties to leave the restaurant.

The restaurant seating system should recognize two types of parties: VIP parties and non-VIP parties.
The seating algorithm must always prioritize VIP parties over non-VIP parties.
Next, the seating algorithm should favour parties that were booked earlier over parties that were booked later.
When seating a party, the seating algorithm should attempt to match the highest priority party with the smallest empty table that can fit the party.
If there is no available table that can fit this party, the seating algorithm should proceed to the next eligible party until a party can be seated.
If none of the parties can be seated, the seating algorithm should throw an exception.

Your goal is to implement a seating algorithm by filling in the method stubs in Restaurant.java.
Feel free to add any additional functionality needed in the other Java source files.
Please ensure that you clean up your solution so that it is as elegant as possible before submission.
