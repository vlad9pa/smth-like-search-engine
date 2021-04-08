package com.example.demo.client;

import com.example.demo.DemoApplication;
import com.example.demo.config.GlobalConfiguration;
import com.example.demo.controler.DocumentController;
import com.example.demo.entity.Document;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.service.DocumentService;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//I know that I can use spring built in mvc test, but I should write something like client
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {DemoApplication.class})
public class ClientTest {

    @Value("${local.server.port}")
    private int port;

    // Too lazy to write sql for testing and execute it before test running /shrug
    @Autowired
    private DocumentRepository repository;

    private DocumentClient documentClient;

    @BeforeEach
    public void setUp(){
        documentClient = new DocumentClient("http://localhost", port, new RestTemplate());
    }

    @Test
    public void shouldFindDocuments(){
        assertFalse(documentClient.findAll().isEmpty());
    }

    @Test
    public void shouldFindByKey(){
        final Document document1 = Document.builder()
                .key(UUID.randomUUID().toString())
                .rawDocument("asdqwe123")
                .build();

        final Document document2 = Document.builder()
                .key(UUID.randomUUID().toString())
                .rawDocument("someRandomWord")
                .build();

        final List<Document> documents = repository.saveAll(Arrays.asList(document1, document2));

        assertTrue(documents.contains(documentClient.findByKey(document1.getKey())));
    }

    @Test
    public void shouldSaveEntity(){
        final Document document = Document.builder()
                .key(UUID.randomUUID().toString())
                .rawDocument("asdqwe123")
                .build();

        final Document response = documentClient.save(document);
        final Document persisted = repository.findByKey(document.getKey());

        assertEquals(persisted, response);
    }


    @Test
    public void shouldWindEntitiesWithWord(){
        final String word = "thisWord";

        final Document document1 = Document.builder()
                .key(UUID.randomUUID().toString())
                .rawDocument("thisWord, word1, word3, word4")
                .build();

        final Document document2 = Document.builder()
                .key(UUID.randomUUID().toString())
                .rawDocument("asd, qwe, 123")
                .build();

        final Document document3 = Document.builder()
                .key(UUID.randomUUID().toString())
                .rawDocument("asd, qwe, thisWordASD")
                .build();

        final List<Document> documents = repository.saveAll(Arrays.asList(document1, document2, document3));

        final List<Document> real = documentClient.findByWord(word);

        final List<Document> expected = documents.stream()
                .filter(it -> it.getKey().equals(document1.getKey()) || it.getKey().equals(document3.getKey()))
                .collect(Collectors.toList());

        assertEquals(expected, real);
    }


}
