package com.book_keeper.library.Repositories.OpenLibrary;

import com.book_keeper.library.Model.Book;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface LookupOpenLibraryRepository {
    Book lookupByIsbn(String isbn) throws IOException, URISyntaxException, InterruptedException;
}
