package com.book_keeper.library.Repositories.OpenLibrary;

import com.book_keeper.library.Model.Book;
import com.book_keeper.library.Model.CoverLinks;
import com.book_keeper.library.Repositories.Books.BookRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenLibraryLookupRepositoryImplTest {
    @Mock
    private OpenLibraryLookupHelper openLibraryLookupHelper;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private OpenLibraryLookupRepositoryImpl OpenLibraryLookupRepositoryImpl;

    @Mock
    private JSONArray jsonArray;

    @Test
    void givenValidIsbn_lookupByIsbn_thenGetABook() throws OpenLibraryLookupException, IOException, URISyntaxException, InterruptedException, JSONException {
        String isbn = "9780439023511";
        String title = "Mockingjay";
        String authors = "author1";
        int pages = 48;
        String  publishers = "publisher1";
        String publishDate = "2006";
        String genres = "genre1";
        String notes = "";
        CoverLinks coverLinks = new CoverLinks();
        coverLinks.setLarge("examplelink.com");
        coverLinks.setMedium("examplelink.com");
        coverLinks.setSmall("examplelink.com");




        Set<String> isbnList = new HashSet<>(List.of(isbn));

        JSONObject requestJson = new JSONObject();
        JSONObject requestInner= new JSONObject();
        requestInner.put("title", title);
        requestInner.put("authors", new JSONArray().put(0, new JSONObject().put("name", authors)));
        requestInner.put("number_of_pages", pages);
        requestInner.put("publishers", new JSONArray().put(0,new JSONObject().put("name", publishers)));
        requestInner.put("publish_date", publishDate);
        requestInner.put("subjects", new JSONArray().put(0,new JSONObject().put("name", genres)));
        requestInner.put("cover", new JSONObject()
                .put("small", "examplelink.com")
                .put("medium", "examplelink.com")
                .put("large", "examplelink.com"));
        requestJson.put("records", new JSONObject().put("key", new JSONObject().put("data", requestInner)));

        Book expectedBooks = new Book();

        expectedBooks.setIsbn(isbn);
        expectedBooks.setTitle(title);
        expectedBooks.setAuthors(Collections.singletonList(authors));
        expectedBooks.setPages(pages);
        expectedBooks.setPublishers(Collections.singletonList(publishers));
        expectedBooks.setPublishDate(publishDate);
        expectedBooks.setGenres(Collections.singletonList(genres));
        expectedBooks.setNotes(notes);
        expectedBooks.setCoverLinks(coverLinks);


        String requestBody = requestJson.toString();
        when(openLibraryLookupHelper.requestIsbnFromOpenLibrary(anyString())).thenReturn(requestBody);
        when(openLibraryLookupHelper.bookInDB(anyString())).thenReturn(new ArrayList<>());

        List<Book> books = OpenLibraryLookupRepositoryImpl.lookupByIsbn(isbnList);
        Book book = books.get(0);

        assert book.getIsbn().equals(expectedBooks.getIsbn());
        assert book.getTitle().equals(expectedBooks.getTitle());
        assert book.getAuthors().equals(expectedBooks.getAuthors());
        assert book.getPages() == expectedBooks.getPages();
        assert book.getPublishers().equals(expectedBooks.getPublishers());
        assert book.getPublishDate().equals(expectedBooks.getPublishDate());
        assert book.getGenres().equals(expectedBooks.getGenres());
        assert book.getNotes().equals(expectedBooks.getNotes());
        assert book.getCoverLinks().getLarge().equals(expectedBooks.getCoverLinks().getLarge());
        assert book.getCoverLinks().getMedium().equals(expectedBooks.getCoverLinks().getMedium());
        assert book.getCoverLinks().getSmall().equals(expectedBooks.getCoverLinks().getSmall());

    }
}


