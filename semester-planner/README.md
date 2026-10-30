##### 1. Why did Java replace `java.util.Date` and `Calendar` with `java.time`? Give two concrete problems the old API had.
- In the old API values were mutable, months ran from 0-11, had confusing names, and poor time-zone support. 

##### 2. When do you use `Period`, and when `Duration`? Use an example from this project. 
- Period is used for dealing with dates such as months, days, and years. Duration is used for things like minutes, seconds, and nanoseconds. In the project, calculating the late fee, Duration was used becuase the method accounted for minutes and Duration would suit best in this scenario. 

##### 3. In the `daylightSavingTime` test, Boise and Phoenix show the *same* clock time in January but are an hour apart in April. Explain why.
- Arizona does not use daylight saving time, so they remain on MST all year. In April, Boise is one hour ahead of Phoenix because of daylight savings. 
##### 4. Your program uses `LocalDate` for class dates but `ZonedDateTime` for the online-student times. Why not use `ZonedDateTime` everywhere?
- ZonedDateTime is useful for students in different areas of the country or world, but could run into bugs when trying to organize dates. It is much easier to organize dates with LocalDate, unless a specific date needs to be changed to a different zone, it is easier to change that specific date instead of handling all dates by zones. 
