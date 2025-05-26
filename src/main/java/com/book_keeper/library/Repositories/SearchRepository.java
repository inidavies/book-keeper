package com.book_keeper.library.Repositories;

import com.book_keeper.library.Model.Book;

import java.util.List;

public interface SearchRepository {
    List<Book> findBySearchTerm(String searchTerm);
}
