package com.softserve.edu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookManagerTest {

  private BookManager bookManager;

  @BeforeEach
  void setUp() {
    bookManager = new BookManager();
    bookManager.addBook(new Book("Adventures of Tom Sawyer", "Mark Twain", "Novel", 1876));
    bookManager.addBook(new Book("The Art of Software Testing", "Glenford J. Myers", "Computer science", 2015));
    bookManager.addBook(new Book("Time Machine", "H.G. Wells", "Fantasy", 2002));
    bookManager.addBook(new Book("Cinderella", "Charles Perrault", "Fairy tale", 1697));
    bookManager.addBook(new Book("Harry Potter and the philosopher's stone", "J. K. Rowling", "Fantasy", 2001));
    bookManager.addBook(new Book("The Shining", "Stephen King", "Horror", 1977));
  }

  @DisplayName("Adding book")
  @Test
  void testAddBookPositive() {
    Book book1 = new Book("Title5", "Author3", "Drama", 2024);
    bookManager.addBook(book1);
    assertEquals(7, bookManager.getBooks().size());
  }

  @DisplayName("Adding empty book")
  @Test
  void testAddBookNull() {
    assertThrows(IllegalArgumentException.class, () -> bookManager.addBook(null));
  }

  @DisplayName("Adding duplicate book")
  @Test
  void testAddDuplicate() {
    Book duplicate = new Book("Adventures of Tom Sawyer", "Mark Twain", "Novel ", 1876);
    assertThrows(IllegalArgumentException.class, () -> bookManager.addBook(duplicate));

  }

  @DisplayName("Removing existing book")
  @Test
  void testRemoveBookPositive() {
    Book book1 = new Book("The Shining", "Stephen King", "Horror", 1977);
    bookManager.removeBookByAuthor(book1.getAuthor());
    assertEquals(5, bookManager.getBooks().size());
  }

  @DisplayName("Removing a non-existent book")
  @Test
  void testRemoveBookNegative() {
    Book book1 = new Book(" The Shining", "S King", "Horror", 1977);
    bookManager.removeBookByAuthor(book1.getAuthor());
    assertEquals(6, bookManager.getBooks().size());
  }
  @ParameterizedTest
  @NullAndEmptySource
  void testProcessInvalidRemoving(String book) {
    System.out.println("Book = " + book);
    if (book != null) {
      System.out.println("\tbook.length() = " + book.length());
    }
  }

  @Test
  void testListAllAuthor() {
    List<String> authors = bookManager.listOfAllAuthor();
    assertEquals(6, authors.size());
    assertTrue(authors.contains("Mark Twain"));
    assertTrue(authors.contains("Glenford J. Myers"));
    assertTrue(authors.contains("H.G. Wells"));
    assertTrue(authors.contains("Charles Perrault"));
    assertTrue(authors.contains("J. K. Rowling"));
    assertTrue(authors.contains("Stephen King"));
  }

  @DisplayName("checking for the presence of an invalid author")
  @Test
  void testInvalidAuthor() {
    List<String> authors = bookManager.listOfAllAuthor();
    assertEquals(6, authors.size());
    assertFalse(authors.contains("Mark Twain2"));
    assertFalse(authors.contains("Glenfor J. Myers"));
    assertFalse(authors.contains("Kristofer Wells"));

  }

  @DisplayName("Positive variant ListAuthorByGenre")
  @ParameterizedTest
  @CsvSource({
          "Fairy tale,1",
          "Fantasy,2",
          "Computer science,1",
          "Horror,1",
          "Novel,1"
  })
  void testListAuthorByGenre(String genre, int expected) {
    List<String> authors = bookManager.listAuthorsByGenre(genre);
    assertEquals(expected, authors.size());
  }

  @DisplayName("Negative variant ListAuthorByGenre")
  @ParameterizedTest
  @CsvSource({
          "Fairy,0",
          "Fantastic,0",
          "Science,0",
          "Horor,0",
          "Novela,0"
  })
  void negativeTestListAuthorByGenre(String genre, int expected) {
    List<String> authors = bookManager.listAuthorsByGenre(genre);
    assertEquals(expected, authors.size());
  }

  @Test
  void testFindBooksByAuthor(){
    String author = "Stephen King";
    Optional<Book> result = bookManager.findBookByAuthor(author);
    assertTrue(result.isPresent(), "The book should be present");
    assertEquals(author, result.get().getAuthor());
  }
  @Test
  void testFindInvalidBooks(){
    String author = "Invalid";
    Optional<Book> result = bookManager.findBookByAuthor(author);
    assertFalse(result.isPresent());
  }
  @ParameterizedTest
  @NullAndEmptySource
  void testProcessInvalidFindingBooksByAuthor(String author) {
    System.out.println("Book = " + author);
    if (author != null) {
      System.out.println("\tbook.length() = " + author.length());
    }
  }
  @Test
  void testFindBooksByGenre(){
    String genre = "Novel";
    Optional<Book> result = bookManager.findBookByGenre(genre);
    assertTrue(result.isPresent(), "The book should be present");
    assertEquals(genre, result.get().getGenre());
  }
  @Test
  void testFindInvalidGenre(){
    String genre = "Invalid";
    Optional<Book> result = bookManager.findBookByGenre(genre);
    assertFalse(result.isPresent());
  }
  @ParameterizedTest
  @NullAndEmptySource
  void testProcessInvalidFindingBooksByGenre(String genre) {
    System.out.println("Book = " + genre);
    if (genre!= null) {
      System.out.println("\tbook.length() = " + genre.length());
    }
  }
  @Test
  void testFindBooksByYear(){
    int year = 2015;
    Optional<Book> result = bookManager.findBookByYear(year);
    assertTrue(result.isPresent(), "The book should be present");
    assertEquals(year, result.get().getPublicYear());
  }
  @Test
  void testFindInvalidYear(){
    int year = 70;
    Optional<Book> result = bookManager.findBookByYear(year);
    assertFalse(result.isPresent());
  }
  @Test
  void testFindBooksByYearThrowsError() {
    IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () -> bookManager.findBookByYear(-1));
    assertEquals("Year must be a positive value!", result.getMessage());
  }
  @ParameterizedTest
  @NullAndEmptySource
  void testProcessInvalidFindingBooksByYear(String year) {
    System.out.println("Book = " + year);
    if (year!= null) {
      System.out.println("\tbook.length() = " +year.length());
    }
  }
  @Test
  void testMergeCollectionsAddsNonDuplicateBooks() {
    List<Book> otherBooks = List.of(
            new Book("Title1", "Author 1", "Adventure", 2024),
            new Book("Title2", "Author 2", "Drama", 2021)
    );
    bookManager.mergeCollections(otherBooks);

    assertEquals(8, bookManager.getBooks().size());
  }

  @Test
  void testMergeCollectionsAddsDuplicateBooks() {
    List<Book> otherBooks = List.of(
            new Book("Adventures of Tom Sawyer", "Mark Twain", "Novel", 1876),
            new Book("The Art of Software Testing", "Glenford J. Myers", "Computer science", 2015));
    bookManager.mergeCollections(otherBooks);
    assertEquals(6, bookManager.getBooks().size());
  }
  @ParameterizedTest
  @NullAndEmptySource
  void testProcessInvalidMerging(String book) {
    System.out.println("Book = " + book);
    if (book!= null) {
      System.out.println("\tbook.length() = " + book.length());
    }
  }

  @ParameterizedTest(name = ("{index}: check with <{0}> => expected <{1}>"))
  @CsvSource({
          "Drama, 0",
          "Science, 0",
          "Fantasy, 2",
          "Fairy tale, 1",
          "Horror, 1",
          "Novel, 1",
          "Computer science,1"
  })
  void testSubCollectionByGenre(String genre, int expectedCount) {
    List<Book> result = bookManager.subCollectionsByGenre(genre);
    assertEquals(expectedCount, result.size());
  }
  @ParameterizedTest
  @NullAndEmptySource
  void testProcessInvalidSubCollection(String genre) {
    System.out.println("Book = " + genre);
    if (genre != null) {
      System.out.println("\tbook.length() = " + genre.length());
    }
  }

}

