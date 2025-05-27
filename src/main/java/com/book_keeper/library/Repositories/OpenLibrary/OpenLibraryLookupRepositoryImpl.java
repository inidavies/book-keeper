package com.book_keeper.library.Repositories.OpenLibrary;

import com.book_keeper.library.Model.Book;
import com.book_keeper.library.Model.CoverLinks;
import com.book_keeper.library.Repositories.Books.BookRepository;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Component
public class OpenLibraryLookupRepositoryImpl implements OpenLibraryLookupRepository {

    @Value("${openlibrary.search.isbnUrl}")
    private String isbnUrl;

    @Autowired
    BookRepository bookRepository;

    private static final Logger logger = Logger.getLogger(OpenLibraryLookupRepository.class.getName());


    public List<Book> lookupByIsbn(Set<String> isbns) throws URISyntaxException, IOException, InterruptedException {

        List<Book> books = new ArrayList<>();
        for (String isbn : isbns) {
            // check if the isbn is already in library
            List<Book> bookInDB = bookInDB(isbn);

            // look it up and add to library if not
            if (bookInDB.isEmpty()){
                URI uri = new URI(isbnUrl + isbn + ".json");
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(uri)
                        .header("Accept", "application/json")
                        .GET()
                        .build();

                HttpClient httpClient = HttpClient.newHttpClient();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();

                try {
                    Book book = jsonToBook(isbn, responseBody);
                    books.add(book);
                    logger.info(isbn + " was added to the database");
                } catch (OpenLibraryLookupException e){
                    logger.info("There was an issue finding " + isbn + " in open library");
                    logger.info(e.getMessage());
                }

            }else{
                logger.info(isbn + " already exists in the database");
                books.add(bookInDB.get(0));
            }

        }

        return books;
    }

    private Book jsonToBook(String isbn, String responseBody) throws OpenLibraryLookupException {
        Book book = new Book();

        try {
            // Get to the inner json object that contains the book data
            JSONObject body = new JSONObject(responseBody);
            JSONObject records = body.getJSONObject("records");

            String recordKey = records.keys().next();


            JSONObject bookInfo = records.getJSONObject(recordKey);
            JSONObject bookData = bookInfo.getJSONObject("data");

            // get data
            String title = getStringData(bookData, "title");

            // get authors
            final List<String> authors = parseFieldData(bookData, "authors");

            //get pages
            int pages = getIntData(bookData, "number_of_pages");

            // get publishers
            final List<String> publishers = parseFieldData(bookData, "publishers");

            // get publish date
            final String publishDate = getStringData(bookData, "publish_date");

            // get genres
            final List<String> genres = parseFieldData(bookData, "subjects");

            // get notes
            String notes = "";

            // get cover links
            CoverLinks coverLinks = new CoverLinks();
            if (bookData.has("cover")){
                Gson gson = new Gson();
                coverLinks = gson.fromJson(bookData.getJSONObject("cover").toString(), CoverLinks.class);
            }

            // set book data
            book.setIsbn(isbn);
            book.setTitle(title);
            book.setAuthors(authors);
            book.setPages(pages);
            book.setPublishers(publishers);
            book.setPublishDate(publishDate);
            book.setGenres(genres);
            book.setNotes(notes);
            book.setCoverLinks(coverLinks);

        } catch (JSONException e){
            throw new OpenLibraryLookupException(e.getMessage());
        }

        return book;
    }

    private List<String> parseFieldData(JSONObject fieldData, String field){
        final List<String> results = new ArrayList<>();
        if (fieldData.has(field)){
            final JSONArray fieldJSON = fieldData.getJSONArray(field);
            for (Object fieldContent : fieldJSON) {
                if (fieldContent instanceof JSONObject) {
                    results.add(((JSONObject) fieldContent).getString("name"));
                }
            }
        }

        return results;
    }

    private String getStringData(JSONObject fieldData, String field){
        if (fieldData.has(field)){
            return fieldData.getString(field);
        }
        return "";
    }

    private int getIntData(JSONObject fieldData, String field){
        if (fieldData.has(field)){
            return fieldData.getInt(field);
        }

        return 0;
    }

    private List<Book> bookInDB(String isbn){
        return bookRepository.findByIsbn(isbn);
    }


}
