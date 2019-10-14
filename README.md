# HashTableImplementation
Implementation of a hash table made specifically for storing IP addresses.

Following textbook ***Algorithms dasgupta papadimitriou vazirani*** Section **1.5 Universal hashing** from ECE 406 (UW)

Key takeaways
* Table size n
  * Prime number little larger than number of items expected in the table
  * Or better to be about twice as large as number of items
* Size of domain of all data items
  * N = n^k (a power of n)
  * Each data item can be considered as k-tuple of integers modulo n
  * H = {ha : a {0, ..., n-1}^k }

Hash function 
* ha(x1, ..., xk) = [ SUM (i=1 to k) {ai*xi} ] mod n

Email c29shen@edu.uwaterloo.ca if you have any questions
