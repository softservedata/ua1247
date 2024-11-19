package com.softserve.edu09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookManagerTest {
    private BookManager bookManager;

    @BeforeEach
    void setUp() {
        bookManager = new BookManager();
        bookManager.addBook(new Book("The Great Adventure", "Alice Johnson", "Drama", 2022));
        bookManager.addBook(new Book("Space Odyssey", "Alice Johnson", "Fantastic", 2024));
        bookManager.addBook(new Book("Life's Journey", "Bob Smith", "Drama", 2021));
        bookManager.addBook(new Book("Science Explained", "Charlie Brown", "Science", 2022));
    }

    @Test
    @DisplayName("Add Book: Successfully adds a new unique book")
    void testAddBookPositive() {
        Book newBook = new Book("New Discoveries", "Diana Green", "Drama", 2024);
        bookManager.addBook(newBook);
        assertEquals(5, bookManager.size(), "The collection should contain 5 books after adding a new book");
    }

    @Test
    @DisplayName("Add Book: Throws exception when attempting to add null")
    void testAddBookNull() {
        assertThrows(IllegalArgumentException.class, () -> bookManager.addBook(null),
                "Adding a null book should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("Add Book: Throws exception when attempting to add a duplicate book")
    void testAddBookDuplicate() {
        Book duplicateBook = new Book("The Great Adventure", "Alice Johnson", "Drama", 2022);
        assertThrows(IllegalArgumentException.class, () -> bookManager.addBook(duplicateBook),
                "Adding a duplicate book should throw IllegalArgumentException");
    }


    @Test
    @DisplayName("Add Book: Allows adding a book with the same author but different title")
    void testAddBookSameAuthorDifferentTitle() {
        String title = "New Book Title";
        String author = "Alice Johnson";
        String genre = "Mystery";
        int publicationYear = 2023;
        Book newBook = new Book(title, author, genre, publicationYear);
        bookManager.addBook(newBook);
        List<Book> books = bookManager.findBooksByGenre(genre);
        assertEquals(1, books.size(), "Expected one book in the 'Mystery' genre");
        assertTrue(books.stream().anyMatch(book -> book.getTitle().equals(title) && book.getAuthor().equals(author)),
                "Expected the new book to be added with the correct title and author");
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("List All Authors: Returns a list of unique authors in the collection")
    void testListAllAuthors() {
        List<String> authors = bookManager.listOfAllAuthors();
        assertEquals(3, authors.size(), "Expected 6 unique authors in the collection");
        assertTrue(authors.contains("Alice Johnson"), "Expected 'Alice Johnson' in the list");
        assertTrue(authors.contains("Bob Smith"), "Expected 'Bob Smith' in the list");
        assertTrue(authors.contains("Charlie Brown"), "Expected 'Charlie Brown' in the list");
    }

    @Test
    @DisplayName("List All Authors: Returns an empty list when the collection is empty")
    void testListAllAuthorsEmptyCollection() {
        bookManager = new BookManager();
        List<String> authors = bookManager.listOfAllAuthors();
        assertTrue(authors.isEmpty(), "Author list should be empty when the collection is empty");
    }


    @Test
    @DisplayName("List Authors by Year: Throws exception for invalid year (non-positive)")
    void testListAuthorsByYearInvalid() {
        assertThrows(IllegalArgumentException.class, () -> bookManager.listAuthorsByYear(0),
                "Year 0 should throw IllegalArgumentException");
        assertThrows(IllegalArgumentException.class, () -> bookManager.listAuthorsByYear(-1),
                "Negative year should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("List Authors by Year: Returns a list with multiple authors when multiple books are found for the given year")
    void testListAuthorsByYearMultipleAuthors() {
        int targetYear = 2022;
        List<String> authors = bookManager.listAuthorsByYear(targetYear);
        assertEquals(2, authors.size(), "Expected two authors for the year 2022");
        assertTrue(authors.contains("Alice Johnson"), "Expected author 'Alice Johnson' in the list");
        assertTrue(authors.contains("Charlie Brown"), "Expected author 'Charlie Brown' in the list");
    }

    @Test
    @DisplayName("List Authors by Year: Returns a list with one author when only one book is found for the given year")
    void testListAuthorsByYearOneBook() {
        int targetYear = 2021;
        List<String> authors = bookManager.listAuthorsByYear(targetYear);
        assertEquals(1, authors.size(), "Expected one author for the year 2021");
        assertTrue(authors.contains("Bob Smith"), "Expected author 'Bob Smith' in the list");
    }

    @Test
    @DisplayName("List Authors by Year: Returns an empty list when no books are found for the given year")
    void testListAuthorsByYearNoBooks() {
        List<String> authors = bookManager.listAuthorsByYear(2000);
        assertTrue(authors.isEmpty(), "The list of authors should be empty for a year with no books");
    }

    @Test
    @DisplayName("List Authors by Genre: Returns all authors when all books belong to the same genre")
    void testListAuthorsByGenreAllSameGenre() {
        String targetGenre = "Drama";
        List<String> authors = bookManager.listAuthorsByGenre(targetGenre);
        assertEquals(2, authors.size(), "Expected two authors for the 'Drama' genre");
        assertTrue(authors.contains("Alice Johnson"), "Expected author 'Alice Johnson' in the list");
        assertTrue(authors.contains("Bob Smith"), "Expected author 'Bob Smith' in the list");
    }

    @ParameterizedTest
    @CsvSource({
            "Drama, 2",
            "Fantastic, 1",
            "Science, 1"
    })
    @DisplayName("List Authors by Genre: Returns correct number of authors by genre")
    void testListAuthorsByGenre(String genre, int expected) {
        List<String> authors = bookManager.listAuthorsByGenre(genre);
        assertEquals(expected, authors.size(),
                String.format("The genre '%s' should have %d unique authors", genre, expected));
    }

    @Test
    @DisplayName("List Authors by Genre: Throws exception when genre is null or empty")
    void testListAuthorsByGenreInvalid() {
        assertThrows(IllegalArgumentException.class, () -> bookManager.listAuthorsByGenre(null),
                "Null genre should throw IllegalArgumentException");
        assertThrows(IllegalArgumentException.class, () -> bookManager.listAuthorsByGenre(""),
                "Empty genre should throw IllegalArgumentException");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("Find Book by Author: Returns the first book when multiple books match the author")
    void testFindBookByAuthorMultipleMatches() {
        String author = "Alice Johnson";
        Optional<Book> book = bookManager.findBookByAuthor(author);
        assertTrue(book.isPresent(), "Expected to find a book by 'Alice Johnson'");
        assertEquals("The Great Adventure", book.get().getTitle(), "Expected the first book by 'Alice Johnson' to be 'The Great Adventure'");
    }


    @Test
    @DisplayName("Find Books by Author: Throws exception for null or empty author")
    void testFindBooksByAuthorInvalid() {
        assertThrows(IllegalArgumentException.class, () -> bookManager.findBookByAuthor(null),
                "Null author should throw IllegalArgumentException");
        assertThrows(IllegalArgumentException.class, () -> bookManager.findBookByAuthor(""),
                "Empty author should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("Find Book by Author: Returns an empty Optional when no book matches the given author")
    void testFindBookByAuthorNoMatch() {
        Optional<Book> result = bookManager.findBookByAuthor("Non-existent Author");
        assertTrue(result.isEmpty(), "Should return an empty Optional when no book matches the given author");
    }


    @Test
    @DisplayName("Find Books by Genre: Successfully finds books of a specific genre")
    void testFindBooksByGenrePositive() {
        String targetGenre = "Drama";
        List<Book> books = bookManager.findBooksByGenre(targetGenre);
        assertEquals(2, books.size(), "Expected two books in the 'Drama' genre");
        assertTrue(books.stream().anyMatch(book -> book.getTitle().equals("The Great Adventure")),
                "Expected 'The Great Adventure' to be found in the 'Drama' genre");
        assertTrue(books.stream().anyMatch(book -> book.getTitle().equals("Life's Journey")),
                "Expected 'Life's Journey' to be found in the 'Drama' genre");
    }


    @Test
    @DisplayName("Find Books by Genre: Returns an empty list when searching for a non-existent genre")
    void testFindBooksByGenreNonExistent() {
        List<Book> books = bookManager.findBooksByGenre("Non-existent Genre");
        assertTrue(books.isEmpty(), "Should return an empty list for a non-existent genre");
    }

    @Test
    @DisplayName("Find Books by Genre: Throws exception when searching with null genre")
    void testFindBooksByGenreNull() {
        assertThrows(IllegalArgumentException.class, () -> bookManager.findBooksByGenre(null),
                "Searching for books with a null genre should throw IllegalArgumentException");
    }


    @Test
    @DisplayName("Find Books by Genre: Returns an empty list when searching in an empty BookManager")
    void testFindBooksByGenreEmptyManager() {
        BookManager emptyManager = new BookManager();
        List<Book> books = emptyManager.findBooksByGenre("Any Genre");
        assertTrue(books.isEmpty(), "Should return an empty list when searching in an empty BookManager");
    }

    @Test
    @DisplayName("Find Books by Year: Returns books from a specific year")
    void testFindBooksByYear() {
        List<Book> books = bookManager.findBooksByYear(2024);
        assertEquals(1, books.size(), "There should be 1 book from the year 2024");
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Space Odyssey")),
                "Book list should contain 'Space Odyssey'");
    }

    @Test
    @DisplayName("Find Books by Year: Returns an empty list when no books are found for a specific year")
    void testFindBooksByYearNotFound() {
        List<Book> books = bookManager.findBooksByYear(2000);
        assertTrue(books.isEmpty(), "No books should be found for the year 2000");
    }

    @Test
    @DisplayName("Find Books by Year: Should handle finding books by year for a year with multiple books")
    void testFindBooksByYearWithMultipleBooks() {
        int targetYear = 2022;
        List<Book> books = bookManager.findBooksByYear(targetYear);
        assertEquals(2, books.size(), "Expected two books for the year 2022");
        assertTrue(books.stream().anyMatch(book -> book.getTitle().equals("The Great Adventure")),
                "Expected 'The Great Adventure' to be found for the year 2022");
        assertTrue(books.stream().anyMatch(book -> book.getTitle().equals("Science Explained")),
                "Expected 'Science Explained' to be found for the year 2022");
    }

    @Test
    @DisplayName("Find Books by Year: Throws exception when attempting to find books by year 0")
    void testFindBooksByYearZero() {
        assertThrows(IllegalArgumentException.class, () -> bookManager.findBooksByYear(0),
                "Finding books with year 0 should throw IllegalArgumentException");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("Remove Books by Author: Removes books by a specific author")
    void testRemoveBooksByAuthor() {
        bookManager.removeBooksByAuthor("Alice Johnson");
        assertEquals(2, bookManager.size(), "The collection should contain 2 books after removing Alice Johnson's books");
    }

    @Test
    @DisplayName("Remove Books by Author: Throws exception for null or empty author")
    void testRemoveBooksByAuthorInvalid() {
        assertThrows(IllegalArgumentException.class, () -> bookManager.removeBooksByAuthor(null),
                "Null author should throw IllegalArgumentException");
        assertThrows(IllegalArgumentException.class, () -> bookManager.removeBooksByAuthor(""),
                "Empty author should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("Remove Books by Author: Does not remove books when author is not found")
    void testRemoveBooksByNonExistentAuthor() {
        bookManager.removeBooksByAuthor("Non Existent Author");
        assertEquals(4, bookManager.size(), "The collection should remain unchanged when the author is not found");
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("Sort Books by Criterion: Should sort books by author")
    void sortBooksByCriterionAuthorPositive() {
        bookManager.sortBooksByCriterion("author");
        List<Book> sortedBooks = bookManager.getBooks();
        assertEquals("Alice Johnson", sortedBooks.get(0).getAuthor(), "First book should be by Alice Johnson");
        assertEquals("Alice Johnson", sortedBooks.get(1).getAuthor(), "Second book should be by Alice Johnson");
        assertEquals("Bob Smith", sortedBooks.get(2).getAuthor(), "Third book should be by Bob Smith");
        assertEquals("Charlie Brown", sortedBooks.get(3).getAuthor(), "4 book should be by Charlie Brown");
    }

    @Test
    @DisplayName("Sort Books by Year: Sorts books in ascending order of publication year")
    void testSortBooksByYearPositive() {
        bookManager.sortBooksByCriterion("year");
        List<Book> books = bookManager.findBooksByGenre("Drama");
        assertEquals("Life's Journey", books.get(0).getTitle(), "Books should be sorted by year in ascending order");
    }

    @Test
    @DisplayName("Sort Books by Title Positive")
    void sortBooksByCriterionTitlePositive() {
        bookManager.sortBooksByCriterion("title");
        List<Book> sortedBooks = bookManager.getBooks();
        assertEquals("Life's Journey", sortedBooks.get(0).getTitle(), "First book should be 'Life's Journey'");
        assertEquals("Science Explained", sortedBooks.get(1).getTitle(), "Second book should be 'Science Explained'");
        assertEquals("Space Odyssey", sortedBooks.get(2).getTitle(), "Third book should be 'Space Odyssey'");
        assertEquals("The Great Adventure", sortedBooks.get(3).getTitle(), "Fourth book should be 'The Great Adventure'");
    }

    @Test
    @DisplayName("Sort Books by Invalid Criterion: Throws exception for invalid sort criterion")
    void testSortBooksByInvalidCriterion() {
        assertThrows(IllegalArgumentException.class, () -> bookManager.sortBooksByCriterion("invalid"),
                "Invalid sort criterion should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("Sort Books by Year: Handles sorting an empty collection")
    void testSortBooksByYearEmptyCollection() {
        bookManager = new BookManager();
        bookManager.sortBooksByCriterion("year");
        assertEquals(0, bookManager.size(), "Sorting an empty collection should not throw an exception");
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("Merge Book Collections: Successfully merges two book collections")
    void testMergeCollections() {
        List<Book> newBooks = List.of(
                new Book("The Lost World", "Alice Johnson", "Adventure", 2023),
                new Book("The Silent Observer", "Bob Smith", "Drama", 2020)
        );
        bookManager.mergeCollections(newBooks);
        assertEquals(6, bookManager.size(), "After merging, the collection should contain 6 books");
    }

    @Test
    @DisplayName("Merge Book Collections: Throws exception when merging with null collection")
    void testMergeCollectionsNull() {
        assertThrows(IllegalArgumentException.class, () -> bookManager.mergeCollections(null),
                "Merging with null collection should throw IllegalArgumentException");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("Subcollection by Genre: Positive")
    void subCollectionByGenrePositive() {
        List<Book> dramaBooks = bookManager.subCollectionByGenre("Drama");
        assertEquals(2, dramaBooks.size(), "There should be 2 books in the 'Drama' genre");
        assertTrue(dramaBooks.stream().allMatch(book -> "Drama".equalsIgnoreCase(book.getGenre())),
                "All books in the subcollection should have the genre 'Drama'");
        assertEquals("The Great Adventure", dramaBooks.get(0).getTitle(), "First book in the 'Drama' genre should be 'The Great Adventure'");
        assertEquals("Life's Journey", dramaBooks.get(1).getTitle(), "Second book in the 'Drama' genre should be 'Life's Journey'");
    }

    @Test
    @DisplayName("Subcollection by Genre: Throws exception for null or empty genre")
    void testSubCollectionByGenreInvalid() {
        assertThrows(IllegalArgumentException.class, () -> bookManager.subCollectionByGenre(null),
                "Null genre should throw IllegalArgumentException");
        assertThrows(IllegalArgumentException.class, () -> bookManager.subCollectionByGenre(""),
                "Empty genre should throw IllegalArgumentException");
    }

}

