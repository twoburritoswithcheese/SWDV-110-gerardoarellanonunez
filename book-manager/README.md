#### 1. In your own words, what is **SQL injection**? Show the SQL that `search` would have built from `' OR '1'='1` if you had used `+` instead of `?`.
SQL injection is a security vulnerablity that can allow a user to interfere with the queries of an application and make changes to its database.

It would build something like:
```
SELECT * FROM books
WHERE title LIKE '%' OR '1'='1%'
OR author LIKE '%' OR '1'='1%'
ORDER BY title;
```

#### 2. Why does every DAO method open and close its own `Connection` with try-with-resources? What could go wrong if you forgot to close them?
Every DAO method opens and closes its own connections so that it can independently perform its own operations. If it is not closed some resources can remain occupied, which could prevent the application from opening additional database connections. 

#### 3. `App` contains no SQL at all. Name two benefits of keeping SQL inside the DAO. (Hint: think about next week.)
One it helps keep things organized and easy to test. Two, the DAO can be reused by other interfaces, for example JavaFX.


#### 4. Paste your `queries.sql` results summary: each query and its row count.

```
-- 1. Every book, newest first
Returned rows: 10

-- 2. Books by Andy Weir, oldest first
Returned rows: 3

-- 3. Unread books, A–Z by title
Returned rows: 3

-- 4. Titles containing 'the' (any case)
Returned rows: 3

-- 5. How many books each author has, most first
Returned rows: 8

-- 6. Average rating of rated books (rating > 0)
Returned rows: 1

-- 7. Mark 'Sapiens' as read with a rating of 4
Result: query executed successfully. Took 3ms, 1 rows affected

-- 8. Delete every unrated, unread book published before 2000
--    (run a SELECT with the same WHERE first – what does it return?)
Result: query executed successfully. Took 0ms, 1 rows affected
```