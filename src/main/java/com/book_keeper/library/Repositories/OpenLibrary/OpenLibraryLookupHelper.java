package com.book_keeper.library.Repositories.OpenLibrary;

import com.book_keeper.library.Model.Book;
import com.book_keeper.library.Repositories.Books.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class OpenLibraryLookupHelper {
    @Value("${openlibrary.search.isbnUrl}")
    private String isbnUrl;

    @Autowired
    BookRepository bookRepository;

    public String requestIsbnFromOpenLibrary(String isbn) throws OpenLibraryLookupException {
        String responseBody = "";
        try{
            URI uri = new URI(isbnUrl + isbn + ".json");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();

        } catch(IOException | InterruptedException | URISyntaxException e){
            throw new OpenLibraryLookupException(e.getMessage());
        }

        return responseBody;
    }

    public List<Book> bookInDB(String isbn){
        return bookRepository.findByIsbn(isbn);
    }

}
