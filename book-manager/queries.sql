-- 1. Every book, newest first
SELECT * FROM books 
ORDER BY year DESC;
    -- Returned rows: 10

-- 2. Books by Andy Weir, oldest first
SELECT * FROM books 
WHERE author = 'Andy Weir' 
ORDER BY year ASC;
    -- Returned rows: 3

-- 3. Unread books, A–Z by title
SELECT * FROM books 
WHERE is_read = 0 
ORDER BY title ASC;
    -- Returned rows: 3

-- 4. Titles containing 'the' (any case)
SELECT * FROM books 
WHERE title 
LIKE '%the%';
    -- Returned rows: 3

-- 5. How many books each author has, most first
SELECT author, COUNT(*) AS book_count 
FROM books
GROUP BY author
ORDER BY book_count DESC;
    -- Returned rows: 8

-- 6. Average rating of rated books (rating > 0)
SELECT AVG(rating) AS average_rating
FROM books
WHERE rating > 0;
    -- Returned rows: 1

-- 7. Mark 'Sapiens' as read with a rating of 4
UPDATE books
SET is_read = 1, rating = 4
WHERE title = 'Sapiens';
    -- Result: query executed successfully. Took 3ms, 1 rows affected

-- 8. Delete every unrated, unread book published before 2000
--    (run a SELECT with the same WHERE first – what does it return?)
SELECT *
FROM books
WHERE rating = 0
AND is_read = 0
AND year < 2000;
    -- Returned rows: 1

DELETE FROM books
WHERE rating = 0
AND is_read = 0
AND year < 2000;
    -- Result: query executed successfully. Took 0ms, 1 rows affected