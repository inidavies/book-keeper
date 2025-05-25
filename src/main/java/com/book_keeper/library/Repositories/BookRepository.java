package com.book_keeper.library.Repositories;

import com.book_keeper.library.Model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository <Book, String> {
}
