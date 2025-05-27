package com.book_keeper.library.Controllers;

import com.book_keeper.library.Model.Book;
import com.book_keeper.library.Repositories.Books.BookRepository;
import com.book_keeper.library.Repositories.Books.SearchBooksRepository;
import com.book_keeper.library.Repositories.OpenLibrary.LookupOpenLibraryRepository;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class LibraryController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    SearchBooksRepository searchRepository;

    @Autowired
    LookupOpenLibraryRepository lookupOpenLibraryRepository;

    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui/");
    }

    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    @PostMapping("/books")
    public List<Book> addBook(@RequestParam String isbn) throws IOException, URISyntaxException, InterruptedException {
        Set<String> isbnList = new HashSet<>(List.of(isbn.split(",")));
        List<Book> books = lookupOpenLibraryRepository.lookupByIsbn(isbnList);
        for (Book book: books) {
            bookRepository.save(book);
        }
        return books;
    }

    @GetMapping("/book")
    public List<Book> findBook(@RequestParam String searchTerm){
        return searchRepository.findBySearchTerm(searchTerm);
    }

    @DeleteMapping("/books")
    public void deleteBook(@RequestParam String id){
        bookRepository.deleteById(id);
    }


}
