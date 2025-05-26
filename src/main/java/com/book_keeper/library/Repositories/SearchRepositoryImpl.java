package com.book_keeper.library.Repositories;

import com.book_keeper.library.Model.Book;
import com.mongodb.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.bson.Document;
import com.mongodb.client.AggregateIterable;

@Component
public class SearchRepositoryImpl implements SearchRepository {

    @Autowired
    MongoClient mongoClient;

    @Autowired
    MongoConverter mongoConverter;

    @Override
    public List<Book> findBySearchTerm(String searchTerm) {
        final List<Book> books = new ArrayList<>();

        MongoDatabase database = mongoClient.getDatabase("library");
        MongoCollection<Document> collection = database.getCollection("books");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                        new Document("text",
                                new Document("query", searchTerm)
                                        .append("path", Arrays.asList("title", "author")))),
                        new Document("$sort",
                                new Document("dateAcquired", 1L)),
                        new Document("$limit", 5L)));

        result.forEach(doc -> books.add(mongoConverter.read(Book.class, doc)));
        return books;
    }
}
