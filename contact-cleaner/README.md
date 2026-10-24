#### 1. Strings are **immutable**. Name a place in your code where forgetting that would have caused a bug.
- A place I see where forgetting to change the string to mutable would be in ContactParser.titleCase(). In this area it would be very tricky to manipulate each individual character. Unless the string name is split to an array and each element in the array is split into another array using toCharArray, but then it starts to get too messy. 
#### 2. Why does `split("|")` behave differently from `split("\\|")`?
   
#### 3. Where did you choose `StringBuilder` instead of `+`, and why?
   
#### 4. Paste the output of `mvn test` (the summary line is enough).