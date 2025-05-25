package com.book_keeper.library.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection ="books")
public class Book {
    @Id
    private String _id;
    private int isbn;
    private String title;
    private String author;
    private String publisher;
    private String description;
    private String category;
    private Date dateAcquired;

}
