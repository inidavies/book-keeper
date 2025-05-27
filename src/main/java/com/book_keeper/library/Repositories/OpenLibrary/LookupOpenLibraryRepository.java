package com.book_keeper.library.Repositories.OpenLibrary;

import com.book_keeper.library.Model.Book;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

public interface LookupOpenLibraryRepository {
    List<Book> lookupByIsbn(Set<String> isbn) throws IOException, URISyntaxException, InterruptedException;
}
