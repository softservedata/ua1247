package com.softserve.edu09;

import java.util.ArrayList;
import java.util.List;

public class DemoBookManager {
    public static void main(String[] args) {
        BookManager manager = new BookManager();
        manager.addBook(new Book("The Great Adventure", "Alice Johnson", "Drama", 2022));
        manager.addBook(new Book("Space Odyssey", "Alice Johnson", "Fantastic", 2024));
        manager.addBook(new Book("Life's Journey", "Bob Smith", "Drama", 2021));
        manager.addBook(new Book("Science Explained", "Charlie Brown", "Science", 2022));


        System.out.println(manager.listOfAllAuthors());
        System.out.println(manager.listAuthorsByGenre("Drama"));
        System.out.println(manager.listAuthorsByYear(2022));

        System.out.println(manager.findBookByAuthor("Alice Johnson"));
        System.out.println(manager.findBooksByGenre("Drama"));
        System.out.println(manager.findBooksByYear(2022));

       //manager.removeBooksByAuthor("Alice Johnson");
       //System.out.println(manager.listOfAllAuthors());

        manager.sortBooksByCriterion("title");
        System.out.println(manager.listOfAllAuthors());
        manager.sortBooksByCriterion("author");
        System.out.println(manager.listOfAllAuthors());
        manager.sortBooksByCriterion("year");
        System.out.println(manager.listOfAllAuthors());

        List<Book> newBooks = new ArrayList<>();
        newBooks.add(new Book("Choke", "Chuck Palahniuk", "Satire", 2001));
        newBooks.add(new Book("All Quiet on the Western Front", "Erich Maria Remarque", "Historical Fiction", 1928));
        manager.mergeCollections(newBooks);
        System.out.println(manager.listOfAllAuthors());

        List<Book> dramaBooks = manager.subCollectionByGenre("Drama");
        System.out.println(dramaBooks);
    }
}
