package com.hendisantika.springbootrestapipostgresql.controller;

import com.hendisantika.springbootrestapipostgresql.entity.Book;
import com.hendisantika.springbootrestapipostgresql.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookRestControllerTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookRestController controller;

    @Test
    public void testAddBook() {
        Book book = new Book();
        book.setName("Spring Boot");
        book.setDescription("A practical guide to building enterprise applications with Spring Boot");
        book.setTags(Arrays.asList("Java", "Spring"));
        when(repository.save(book)).thenReturn(book);

        ResponseEntity<?> response = controller.addBook(book);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(repository, times(1)).save(book);
    }

    @Test
    public void testGetAllBooks() {
        Book book1 = new Book();
        book1.setName("Spring Boot");
        book1.setDescription("A practical guide to building enterprise applications with Spring Boot");
        book1.setTags(Arrays.asList("Java", "Spring"));
        Book book2 = new Book();
        book2.setName("Angular");
        book2.setDescription("A comprehensive guide to building web applications with Angular");
        book2.setTags(Arrays.asList("JavaScript", "Angular"));
        when(repository.findAll()).thenReturn(Arrays.asList(book1, book2));

        ResponseEntity<Collection<Book>> response = controller.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetBookWithId() {
        Book book = new Book();
        book.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        ResponseEntity<Book> response = controller.getBookWithId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(repository, times(1)).findById(1L);
    }
}

