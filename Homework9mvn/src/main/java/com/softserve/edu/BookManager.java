package com.softserve.edu;

import org.jetbrains.annotations.TestOnly;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BookManager {
    private List<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public void addBook(Book book) {
        if (book == null) throw new IllegalArgumentException("Book can not be null!");
        if (books.stream().anyMatch(b -> b.getTitle().equalsIgnoreCase(book.getTitle())
                && b.getAuthor().equalsIgnoreCase(book.getAuthor()))) {
            throw new IllegalArgumentException("Duplicate book entry");
        }
        books.add(book);

    }

    public List<String> listOfAllAuthor() {
        return books.stream()
                .map(Book::getAuthor)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> listAuthorsByGenre(String genre) {
        if (genre == null || genre.isEmpty()) throw new IllegalArgumentException("Genre can not be a null or empty");
        return books.stream()
                .filter(b -> b.getGenre().equalsIgnoreCase(genre))
                .map(Book::getAuthor)
                .distinct()
                .collect(Collectors.toList());

    }

    public List<String> listAuthorsByYear(int year) {
        if (year <= 0) throw new IllegalArgumentException("Year must be positive!");
        return books.stream()
                .filter(b -> b.getPublicYear() == year)
                .map(Book::getAuthor)
                .distinct()
                .collect(Collectors.toList());

    }

    public Optional<Book> findBookByAuthor(String author) {
        if (author == null || author.isEmpty()) throw new IllegalArgumentException("Author can not be a null or empty");
        return books.stream()
                .filter(b -> b.getAuthor().equalsIgnoreCase(author))
                .findFirst();
    }

    public Optional<Book> findBookByYear(int year) {
        if (year <= 0) throw new IllegalArgumentException("Year must be positive!");
        return books.stream()
                .filter(b -> b.getPublicYear() == year)
                .findFirst();
    }

    public Optional<Book> findBookByGenre(String genre) {
        if (genre == null || genre.isEmpty()) throw new IllegalArgumentException("Genre can not be a null or empty");
        return books.stream()
                .filter(b -> b.getGenre().equalsIgnoreCase(genre))
                .findFirst();
    }

    public void removeBookByAuthor(String author) {
        if (author == null || author.isEmpty()) throw new IllegalArgumentException("Author can not be a null or empty");
        books.removeIf(b -> b.getAuthor().equalsIgnoreCase(author));

    }


    public List<Book> sortedBooksByYear() {
        return books.stream()
                .sorted(Comparator.comparingInt(Book::getPublicYear))
                .collect(Collectors.toList());
    }
    public void mergeCollections(List<Book> someBooks) {
        if (someBooks == null) throw new IllegalArgumentException("Collection can not be null");
        for (Book book : someBooks) {
            if (book == null) continue;
            if (books.stream()
                    .noneMatch(b -> b.getTitle().equalsIgnoreCase(book.getTitle())
                            && b.getAuthor().equalsIgnoreCase(book.getAuthor()))) {
                books.add(book);
            }
        }
    }

    public List<Book> subCollectionsByGenre(String genre) {
        if (genre == null || genre.isEmpty()) throw new IllegalArgumentException("Genre can not be a null or empty");
        return books
                .stream()
                .filter(b -> b.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }


}