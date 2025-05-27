package com.book_keeper.library.Repositories.Books;

import com.book_keeper.library.Model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository <Book, String> {
    List<Book> findByIsbn(String isbn);
}
