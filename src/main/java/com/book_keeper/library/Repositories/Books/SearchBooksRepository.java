package com.book_keeper.library.Repositories.Books;

import com.book_keeper.library.Model.Book;

import java.util.List;

public interface SearchBooksRepository {
    List<Book> findBySearchTerm(String searchTerm);
}
