#### 1. Strings are **immutable**. Name a place in your code where forgetting that would have caused a bug.
- A place I see where forgetting to change the string to mutable would be in ContactParser.titleCase(). In this area it would be very tricky to manipulate each individual character. Unless the string name is split to an array and each element in the array is split into another array using toCharArray, but then it starts to get too messy. 
#### 2. Why does `split("|")` behave differently from `split("\\|")`?
- The String.split() method uses regex as its parameter so, one is treated as an OR while the other is interpreted as a literal "|". It will either split the string at each ch or split the string at each literal "|". 
#### 3. Where did you choose `StringBuilder` instead of `+`, and why?
- In ContactParser.normalizeName() I used Stringbuilder to capitalize the first letter of each name. I could of used +, I would have to re-do my work, but in the moment using Stringbuilder just seemed to be the best option. I felt like I had more control of what I wanted to do to the string.
#### 4. Paste the output of `mvn test` (the summary line is enough).
- [INFO] Running edu.cwi.swdv110.contacts.ContactParserTest
email is short
[INFO] Tests run: 20, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.087 s -- in edu.cwi.swdv110.contacts.ContactParserTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.320 s
[INFO] Finished at: 2026-10-23T18:04:16-06:00
[INFO] ------------------------------------------------------------------------
