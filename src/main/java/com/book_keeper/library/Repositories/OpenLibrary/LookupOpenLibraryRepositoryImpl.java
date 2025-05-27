package com.book_keeper.library.Repositories.OpenLibrary;

import com.book_keeper.library.Model.Book;
import com.book_keeper.library.Model.CoverLinks;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
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

@Component
public class LookupOpenLibraryRepositoryImpl implements LookupOpenLibraryRepository {

    @Value("${openlibrary.search.isbnUrl}")
    private String isbnUrl;

    public List<Book> lookupByIsbn(Set<String> isbns) throws URISyntaxException, IOException, InterruptedException {
        List<Book> books = new ArrayList<>();
        for (String isbn : isbns) {
            URI uri = new URI(isbnUrl + isbn + ".json");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            Book book = jsonToBook(isbn, responseBody);
            books.add(book);
        }

        return books;
    }

    private Book jsonToBook(String isbn, String responseBody) {
        // TODO: impl try catch
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


        Book book = new Book();

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


        return book;
    }

    private List<String> parseFieldData(JSONObject fieldData, String field){
        final JSONArray fieldJSON = fieldData.getJSONArray(field);
        final List<String> results = new ArrayList<>();
        if (fieldData.has(field)){
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

}
