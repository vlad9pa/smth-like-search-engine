package com.example.demo.service;

import com.example.demo.entity.Document;
import com.example.demo.repository.DocumentRepository;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class DocumentServiceTest {

    @MockBean
    private DocumentRepository repository;

    @Autowired
    private DocumentService documentService;


    @Test
    @SneakyThrows
    public void shouldSaveDocument(){
        final Document dto = Document.builder()
                .key(UUID.randomUUID().toString())
                .rawDocument("randomStinrg123")
                .build();

        final Document expected = dto.toBuilder().id(UUID.randomUUID()).build();

        Mockito.when(repository .save(any())).thenReturn(expected);

        final Document real = documentService.save(dto);

        assertEquals(expected, real);

        Mockito.verify(repository, Mockito.times(1)).save(dto);
    }

    @Test
    @SneakyThrows
    public void shouldFindAllDocuments(){
        final List<Document> expected = Collections.singletonList(Document.builder()
                .id(UUID.randomUUID())
                .key(UUID.randomUUID().toString())
                .rawDocument("randomStinrg123")
                .build());

        Mockito.when(repository.findAll()).thenReturn(expected);

        final List<Document> real = documentService.findAll();

        assertEquals(expected, real);

        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    @SneakyThrows
    public void shouldFindAllDocumentsBySpecificWord(){

        final List<Document> expected = Collections.singletonList(Document.builder()
                .id(UUID.randomUUID())
                .key(UUID.randomUUID().toString())
                .rawDocument("randomStinrg123")
                .build());

        Mockito.when(repository.findByWord(any())).thenReturn(expected);

        final List<Document> real = documentService.findByWord("123");

        assertEquals(expected, real);

        Mockito.verify(repository, Mockito.times(1)).findByWord("123");
    }

    @Test
    @SneakyThrows
    public void shouldFindAllDocumentsByKey(){

        final Document expected = Document.builder()
                .id(UUID.randomUUID())
                .key(UUID.randomUUID().toString())
                .rawDocument("randomStinrg123")
                .build();

        Mockito.when(repository.findByKey(any())).thenReturn(expected);

        final Document real = documentService.findByKey(expected.getKey());

        assertEquals(expected, real);

        Mockito.verify(repository, Mockito.times(1)).findByKey(expected.getKey());
    }
}
