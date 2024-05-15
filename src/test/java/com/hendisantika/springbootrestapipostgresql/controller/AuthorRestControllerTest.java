package com.hendisantika.springbootrestapipostgresql.controller;

import com.hendisantika.springbootrestapipostgresql.entity.Author;
import com.hendisantika.springbootrestapipostgresql.repository.AuthorRepository;
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
public class AuthorRestControllerTest {

    @Mock
    private AuthorRepository repository;

    @InjectMocks
    private AuthorRestController controller;

    @Test
    public void testAddAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        when(repository.save(author)).thenReturn(author);

        ResponseEntity<?> response = controller.addAuthor(author);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(author, response.getBody());
        verify(repository, times(1)).save(author);
    }

    @Test
    public void testGetAllAuthors() {
        Author author1 = new Author();
        author1.setFirstName("John");
        author1.setLastName("Doe");
        Author author2 = new Author();
        author2.setFirstName("Jane");
        author2.setLastName("Doe");
        when(repository.findAll()).thenReturn(Arrays.asList(author1, author2));

        ResponseEntity<Collection<Author>> response = controller.getAllAuthors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetAuthorWithId() {
        Author author = new Author();
        author.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(author));
        ResponseEntity<Author> response = controller.getAuthorWithId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(author, response.getBody());
        verify(repository, times(1)).findById(1L);
    }
}
