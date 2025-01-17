package com.juliostmendes.literAlura.main;

import com.fasterxml.jackson.databind.JsonNode;
import com.juliostmendes.literAlura.model.Author;
import com.juliostmendes.literAlura.model.Book;
import com.juliostmendes.literAlura.model.BookData;
import com.juliostmendes.literAlura.repository.BookRepository;
import com.juliostmendes.literAlura.service.ApiConsumer;
import com.juliostmendes.literAlura.service.ApiConverter;
import com.juliostmendes.literAlura.service.AuthorService;
import com.juliostmendes.literAlura.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Main {
    Scanner sc = new Scanner(System.in);
    private static final String URL = "https://gutendex.com/books/?";

    private final ApiConsumer consumer = new ApiConsumer();

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;

    public void showMenu() {

        var option = -1;
        while(option != 0) {
            System.out.println(
                    """
                            Choose your option:
                            1- Search book by title
                            2- List registered books
                            3- List registered author
                            4- List authors alive by year
                            5- List books by language
                            0 - Exit
                            
                            Enter your option:""");

            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    searchBookWeb();
                    break;
                case 0:
                    System.out.println("Thank you, have a nice day :)");
                    break;
                case 2:
                    listRegisteredBooks();
                    break;
                case 3:
                    listRegisteredAuthors();
                    break;
                case 4:
                    listAuthorsAliveByYear();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }



    public void searchBookWeb(){
        System.out.println("Search by the book name:");
        String searchQuery = sc.nextLine();

        JsonNode apiResponse = consumer.getApiResponse(URL + "search=" + searchQuery.toLowerCase().replace(" ", "%20"));

        // Ex
        JsonNode resultsNode = apiResponse.get("results");
        if (resultsNode != null && resultsNode.isArray() && resultsNode.size() > 0) {
            JsonNode firstBookNode = resultsNode.get(0);

            BookData bookData = ApiConverter.convertToBookData(firstBookNode);

            bookService.saveBook(bookData);

            System.out.println("First book saved successfully!");
        } else {
            System.out.println("No books found in the response.");
        }
    }
    public void listRegisteredBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found in the database.");
        } else {
            books.forEach(book -> {
                System.out.println(book.getTitle());
            });
        }
    }

    public void listRegisteredAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        if (authors.isEmpty()) {
            System.out.println("No authors found in the database.");
        } else {
            authors.forEach(author -> {
                System.out.println(author.getName());
            });
        }
    }


    public void listAuthorsAliveByYear() {
        System.out.println("Enter the year to check for authors alive:");
        int year = sc.nextInt();
        sc.nextLine();

        List<Author> authors = authorService.getAllAuthors();

        boolean found = false;
        for (Author author : authors) {
            int birthYear = author.getBirthYear();
            int deathYear = author.getDeathYear();

            if (birthYear <= year && deathYear >= year) {
                System.out.println("Author " + author.getName() + " was alive in " + year + ".");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No authors found alive in the year " + year + ".");
        }
    }



    public void listBooksByLanguage() {
        System.out.println("""
                en: english
                pt: portuguese
                fr: french
                es: spanish
                ru: russian""");
        System.out.println("Enter the language to filter books:");
        String language = sc.nextLine().toLowerCase();

        List<Book> books = bookService.getBooksByLanguage(language);

        if (books.isEmpty()) {
            System.out.println("No books found in the language: " + language);
        } else {
            System.out.println("Books find in " + language);
            books.forEach(book -> {
                System.out.println(book.getTitle() + ", Language: " + book.getLanguage());
            });
        }

    }

}
