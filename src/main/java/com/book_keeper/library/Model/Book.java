package com.book_keeper.library.Model;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
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
    private String[] authors;
    private int pages;
    private String[] publishers;
    private String publishDate;
    private String[] genres;
    private Date dateAcquired = new Date();
    private String notes;

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

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String[] getPublishers() {
        return publishers;
    }

    public void setPublishers(String[] publishers) {
        this.publishers = publishers;
    }

    public int getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(int publishDate) {
        this.publishDate = publishDate;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public Date getDateAcquired() {
        return dateAcquired;
    }

    public void setDateAcquired(Date dateAcquired) {
        this.dateAcquired = dateAcquired;
    }

    @Override
    public String toString() {
        return "Book{" +
                "_id='" + _id + '\'' +
                ", isbn=" + isbn +
                ", title='" + title + '\'' +
                ", authors=" + Arrays.toString(authors) +
                ", pages=" + pages +
                ", publishers=" + Arrays.toString(publishers) +
                ", publishDate=" + publishDate +
                ", genres=" + Arrays.toString(genres) +
                ", dateAcquired=" + dateAcquired +
                '}';
    }
}
