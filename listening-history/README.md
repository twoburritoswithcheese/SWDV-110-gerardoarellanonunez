#### 1. Pick one method in `MusicStats` and rewrite it with a `for` loop in your README. Which version is easier to read? Which is easier to change?
- MusicStats.totalMinutes() method - 
```java  
public static long totalMinutes(List<Play> plays, Predicate<Play> filter) {

    int totalSeconds = 0;

    for (Play play : plays) {
        if (filter.test(play)) {
            totalSeconds += play.seconds();
        }
    }

    return totalSeconds / 60;
}
```
- The stream version is easier to read because it is shorter and clearly shows each step of the process. Also, the stream seems easier to change because its easy to add or remove steps. 
#### 2. `PlayFormatter` and `Function<Play, String>` have the same shape. Why might a team still create its own functional interface?
- A team might create its own functional interface because it gives more flexebility and control of what the function does. It can be easy to add more features to the function or use syntax that is easy for others to read. 
#### 3. Why must the list passed to `PlayCleaner.clean` be an `ArrayList` and not the result of `List.of(...)` or `stream().toList()`?
- The method requires changes to be made to the `ArrayList`, but `List.of(...)` is immutable, and `stream().tolist()` returns an immutable list. It would require more code to copy the list and replace it with a new list with the new changes. 
#### 4. A stream does nothing until a terminal operation runs. Name three terminal operations you used.
Three stream terminal operations used.
1. `toList()`, 
2. `count()`, 
3. `sum()`