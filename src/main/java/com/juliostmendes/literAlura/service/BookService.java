package com.juliostmendes.literAlura.service;

import com.juliostmendes.literAlura.model.Author;
import com.juliostmendes.literAlura.model.AuthorData;
import com.juliostmendes.literAlura.model.Book;
import com.juliostmendes.literAlura.model.BookData;
import com.juliostmendes.literAlura.repository.AuthorRepository;
import com.juliostmendes.literAlura.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    public void saveBook(BookData bookData) {

        AuthorData authorData = bookData.author();
        Author author = authorRepository.findByName(authorData.name())
                .orElseGet(() -> new Author(authorData));

        if (author.getId() == null) {
            authorRepository.save(author);
        }

        Book book = new Book(bookData, author);
        bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByLanguage(String language) {
        return bookRepository.findByLanguage(language);
    }


}
