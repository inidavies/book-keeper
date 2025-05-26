package com.book_keeper.library.Repositories.OpenLibrary;
import com.book_keeper.library.Model.Book;
import com.book_keeper.library.Model.CoverLinks;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.swagger.v3.core.util.Json;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.DataInput;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Component
public class LookupOpenLibraryRepositoryImpl implements LookupOpenLibraryRepository {

    @Value("${openlibrary.search.isbnUrl}")
    private String isbnUrl;

    public Book lookupByIsbn(String isbn) throws URISyntaxException, IOException, InterruptedException {
        URI uri = new URI(isbnUrl + isbn + ".json");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        return jsonToBook(isbn, responseBody);
    }

    private Book jsonToBook(String isbn, String responseBody) {
        // TODO: impl try catch
        // Get to the inner json object that contains the book data
        JSONObject body = new JSONObject(responseBody);
        JSONObject records = body.getJSONObject("records");
        ;
        String recordKey = records.keys().next();


        JSONObject bookInfo = records.getJSONObject(recordKey);
        JSONObject bookData = bookInfo.getJSONObject("data");

        // get data
        String title = bookData.getString("title");

        // get authors
        List<String> authors = parseFieldData(bookData, "authors");

        //get pages
        int pages = bookData.getInt("number_of_pages");

        // get publishers
        List<String> publishers = parseFieldData(bookData, "publishers");

        // get publish date
        String publishDate = bookData.getString("publish_date");

        // get genres
        List<String> genres = parseFieldData(bookData, "subjects");

        // get notes
        String notes = "";

        // get cover links
        Gson gson = new Gson();
        CoverLinks coverLinks = gson.fromJson(bookData.getJSONObject("cover").toString(), CoverLinks.class);

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
        JSONArray fieldJSON = fieldData.getJSONArray(field);
        List<String> results = new ArrayList<>();
        for (Object fieldContent : fieldJSON) {
            if (fieldContent instanceof JSONObject) {
                results.add(((JSONObject) fieldContent).getString("name"));
            }
        }
        return results;
    }

}
