#### 1. 
### Sorting Benchmark Results

| Number of Elements | Selection Sort (ms) | Merge Sort (ms) |
|---:|---:|---:|
| 1,000 | 3.0 | 0.2 |
| 2,000 | 13.2 | 0.5 |
| 4,000 | 51.0 | 1.0 |
| 8,000 | 217.3 | 2.4 |
| 16,000 | 887.6 | 4.9 |



 - Selection Sort has a time of O(n²). As the number of elements increases, the number of comparisons grows about 4x. 

- Merge Sort has a time of O(n log n). It divides the dataset into smaller sections, sorts them, and merges the results. This makes it way more efficient for larger datasets.

#### 2. What is the Big-O of totalSize in terms of f, the number of files and folders? Why?

The `totalSize()` method has a time of O(f).

The method recursively visits each file and folder once. For each directory, it retrieves its children and calculates their total sizes. Each entry contributes to the final result one time. It doesn't repeatedly sort or compare every file against every other file. 

#### 3. Binary search needs a sorted list. If you only need to search once, is it worth sorting first? What if you search 1,000 times?

If the search needs to be done only once, then it is not necessarily worth sorting because the sorting adds time and binary tends to be a little faster than sorting, or if the list is small enough a linear search may work as well. However, if a search needs to be done 1,000 times, then sorting would be beneficial, because performing 1,000 linear searches would add so much time slowing down the search process.

#### 4. What would happen if `maxDepth` had no base case? What error would Java throw, and why?

If the `maxDepth()` method did not have a base case, it would continue making recursive calls without a stopping point, almost like an infinite loop. Java would throw a `StackOverflowError` because it ran out of call stack space.

#### 5. Name one more real-world problem (not folders) that has a recursive structure.

A big corporation. There are employees and each employee has a manager, and that manager has other managers above them, so on until it reaches the CEO. 