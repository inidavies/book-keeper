package com.book_keeper.library.Controllers;

import com.book_keeper.library.Model.Book;
import com.book_keeper.library.Repositories.BookRepository;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class LibraryController {

    @Autowired
    BookRepository bookRepository;

    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui/");
    }

    @GetMapping("/books")
    public List<Book> books(){
        return bookRepository.findAll();
    }

}
