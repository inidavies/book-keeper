package com.book_keeper.library.Model;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection ="books")
public class Book {
    @Id
    private String _id;
    private String isbn;
    private String title;
    private List<String> authors;
    private int pages;
    private List<String> publishers;
    private String publishDate;
    private List<String> genres;
    private Date dateAcquired = new Date();
    private String notes;
    private CoverLinks coverLinks;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<String> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<String> publishers) {
        this.publishers = publishers;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public List<String> etGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Date getDateAcquired() {
        return dateAcquired;
    }

    public void setDateAcquired(Date dateAcquired) {
        this.dateAcquired = dateAcquired;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public CoverLinks getCoverLinks() {
        return coverLinks;
    }

    public void setCoverLinks(CoverLinks coverLinks) {
        this.coverLinks = coverLinks;
    }

    @Override
    public String toString() {
        return "Book{" +
                "_id='" + _id + '\'' +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", pages=" + pages +
                ", publishers=" + publishers +
                ", publishDate='" + publishDate + '\'' +
                ", genres=" + genres +
                ", dateAcquired=" + dateAcquired +
                ", notes='" + notes + '\'' +
                '}';
    }
}
