package com.javacs.concepts.collections.ex;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

record Book(String bookName,
            String bookAuthor,
            int bookPublishYear,
            List<String> bookGenres) {

    Book {
        bookName = requireText(bookName, "Book Name");
        bookAuthor = requireText(bookAuthor, "Book Author");

        if (bookPublishYear < 0 || bookPublishYear > Year.now().getValue())
            throw new LibraryExceptions.BookInformationMissingException(
                    "Invalid publish year: " + bookPublishYear);

        Objects.requireNonNull(bookGenres, "Book Genres");
        if (bookGenres.isEmpty())
            throw new LibraryExceptions.BookInformationMissingException(
                    "Write at least one genre");

        bookGenres = List.copyOf(bookGenres);

        for (String g : bookGenres)
            requireText(g, "Genre");
    }

    @Contract("null, _ -> fail")
    private static @NotNull String requireText(String value, String field) {

        if (value == null || value.isBlank())
            throw new LibraryExceptions.BookInformationMissingException(
                    field + " cannot be null or blank");

        return value;
    }
}

public class Library {

    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(Objects.requireNonNull(book, "book"));
    }

    public int deleteAuthorBooks(String authorName) {

        if (authorName == null || authorName.isBlank())
            throw new LibraryExceptions.InvalidInputException("authorName is required");

        int before = books.size();
        books.removeIf(book -> book.bookAuthor().equals(authorName));

        return before - books.size();
    }

    public List<Book> getBooks() {
        return List.copyOf(books);
    }

    public String report() {

        StringBuilder sb = new StringBuilder();

        for (Book b : books) {
            sb.append("=".repeat(45)).append('\n')
                    .append("Book Name: ").append(b.bookName()).append('\n')
                    .append("Book Author: ").append(b.bookAuthor()).append('\n')
                    .append("Book Published Year: ").append(b.bookPublishYear()).append('\n')
                    .append("Book Genres: ").append(b.bookGenres()).append('\n');
        }

        return sb.toString();
    }

    @Override public String toString() {
        return "Report:\n" + report();
    }
}

final class LibraryExceptions {

    private LibraryExceptions() {}

    static class BookInformationMissingException extends IllegalArgumentException {
        BookInformationMissingException(String message) {
            super(message);
        }
    }

    static class InvalidInputException extends IllegalArgumentException {
        InvalidInputException(String message) {
            super(message);
        }
    }
}

class Runner {
    public static void main(String[] args) {

        Library library = new Library();

        library.addBook(new Book("book1", "Arsam", 2020, List.of("code", "tech")));
        library.addBook(new Book("book2", "Rick", 2020, List.of("code", "web")));

        System.out.println("Deleted: " + library.deleteAuthorBooks("Arsam"));

        System.out.println(library);
    }
}