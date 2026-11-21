package edu.cwi.swdv110.books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Every SQL statement for the books table lives in this class.
 * RULES: every method opens its own Connection in try-with-resources,
 * and every value from outside goes in through a ? parameter – never with +.
 */
public class BookDAO {

    private final String url;

    public BookDAO(String url) {
        this.url = url;
    }

    /** Helper: builds a Book from the CURRENT row of a ResultSet. is_read is stored as 0/1. */
    private Book toBook(ResultSet rs) throws SQLException {
        return new Book(
        rs.getInt("id"),
        rs.getString("isbn"),
        rs.getString("title"),
        rs.getString("author"),
        rs.getInt("year"),
        rs.getInt("rating"),
        rs.getInt("is_read") == 1);

    }

    // ---------------- Part 2: reading ----------------

    /** All books ordered by title. */
    public List<Book> getAll() throws SQLException {
        String sql = "SELECT * FROM books ORDER BY title";

        List<Book> books = new ArrayList<>();

        try (Connection conn = Database.connect(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books.add(toBook(rs));
            }
        }

        return books;
    }

    /** The book with this exact ISBN, or Optional.empty(). */
    public Optional<Book> findByIsbn(String isbn) throws SQLException {
        String sql = "SELECT * FROM books WHERE isbn = ?";

        try (Connection conn = Database.connect(url);
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, isbn);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(toBook(rs));
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Books whose title OR author contains term (SQLite's LIKE ignores case for A–Z),
     * ordered by title. Hint: the % wildcards go in the PARAMETER, not the SQL:
     *   ps.setString(1, "%" + term + "%");
     */
    public List<Book> search(String term) throws SQLException {
        String sql = """
            SELECT *
            FROM books
            WHERE title LIKE ? OR author LIKE ?
            ORDER BY title
            """;

        List<Book> books = new ArrayList<>();

        try (Connection conn = Database.connect(url);
            PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchTerm = "%" + term + "%";

            ps.setString(1, searchTerm);
            ps.setString(2, searchTerm);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    books.add(toBook(rs));
                }
            }
        }

        return books;
    }

    /** Books not yet read, ordered by title. */
    public List<Book> unread() throws SQLException {
        String sql = "SELECT * FROM books WHERE is_read = 0 ORDER BY title";

        List<Book> books = new ArrayList<>();

        try (Connection conn = Database.connect(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books.add(toBook(rs));
            }
        }
        return books;
    }

    // ---------------- Part 3: writing ----------------

    /**
     * Inserts the book (ignore b.id() – the database assigns it) and returns the new id.
     * Hint: prepareStatement(sql, Statement.RETURN_GENERATED_KEYS), then getGeneratedKeys().
     * A duplicate ISBN makes the database throw SQLException – let it propagate.
     */
    public int add(Book b) throws SQLException {
        String sql = """
            INSERT INTO books (isbn, title, author, year, rating, is_read)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = Database.connect(url);
            PreparedStatement ps = conn.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, b.isbn());
            ps.setString(2, b.title());
            ps.setString(3, b.author());
            ps.setInt(4, b.year());
            ps.setInt(5, b.rating());
            ps.setInt(6, b.read() ? 1 : 0);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        throw new SQLException("Could not retrieve generated book ID.");
    }

    /** Updates every column of the row whose id = b.id(). True if a row changed. */
    public boolean update(Book b) throws SQLException {
        String sql = """
            UPDATE books
            SET isbn = ?, title = ?, author = ?,
                year = ?, rating = ?, is_read = ?
            WHERE id = ?
            """;

        try (Connection conn = Database.connect(url);
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.isbn());
            ps.setString(2, b.title());
            ps.setString(3, b.author());
            ps.setInt(4, b.year());
            ps.setInt(5, b.rating());
            ps.setInt(6, b.read() ? 1 : 0);
            ps.setInt(7, b.id());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Marks a book read and sets its rating. Rating outside 1–5 ->
     * IllegalArgumentException BEFORE touching the database. True if a row changed.
     */
    public boolean markRead(String isbn, int rating) throws SQLException {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        String sql = """
            UPDATE books
            SET is_read = 1, rating = ?
            WHERE isbn = ?
            """;

        try (Connection conn = Database.connect(url);
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rating);
            ps.setString(2, isbn);

            return ps.executeUpdate() > 0;
        }
    }

    /** Deletes by ISBN. True if a row was deleted. */
    public boolean delete(String isbn) throws SQLException {
        String sql = "DELETE FROM books WHERE isbn = ?";

        try (Connection conn = Database.connect(url);
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, isbn);

            return ps.executeUpdate() > 0;
        }
    }

    // ---------------- Part 4: reports ----------------

    /**
     * author -> number of books, most books first, ties by author A–Z.
     * Let SQL do the work: GROUP BY, COUNT(*), ORDER BY. Use a LinkedHashMap to keep that order.
     */
    public Map<String, Integer> countByAuthor() throws SQLException {
        String sql = """
            SELECT author, COUNT(*) AS book_count
            FROM books
            GROUP BY author
            ORDER BY book_count DESC, author ASC
            """;

        Map<String, Integer> counts = new LinkedHashMap<>();

        try (Connection conn = Database.connect(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                counts.put(
                    rs.getString("author"),
                    rs.getInt("book_count")
                );
            }
        }

        return counts;
    }

    /** Average rating of RATED books (rating > 0); 0.0 if none are rated. Use SQL's AVG. */
    public double averageRating() throws SQLException {
        String sql = """
            SELECT AVG(rating) AS average_rating
            FROM books
            WHERE rating > 0
            """;

        try (Connection conn = Database.connect(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("average_rating");
            }
        }

        return 0.0;
    }
}