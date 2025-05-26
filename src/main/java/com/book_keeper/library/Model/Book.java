package com.book_keeper.library.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.Date;

@Data
@AllArgsConstructor
@Document(collection ="books")
public class Book {
    @Id
    private String _id;
    private int isbn;
    private String title;
    private String[] author;
    private String[] publisher;
    private String description;
    private String[] categories;
    private Date dateAcquired;

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "_id=" + _id +
                ", isbn=" + isbn +
                ", title='" + title + '\'' +
                ", author=" + Arrays.toString(author) +
                ", publisher=" + Arrays.toString(publisher) +
                ", description='" + description + '\'' +
                ", categories=" + Arrays.toString(categories) +
                ", dateAcquired=" + dateAcquired +
                '}';
    }

    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getPublisher() {
        return publisher;
    }

    public void setPublisher(String[] publisher) {
        this.publisher = publisher;
    }

    public String[] getAuthor() {
        return author;
    }

    public void setAuthor(String[] author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public Date getDateAcquired() {
        return dateAcquired;
    }

    public void setDateAcquired(Date dateAcquired) {
        this.dateAcquired = dateAcquired;
    }

}
