package com.example.demo.client;

import com.example.demo.entity.Document;
import java.net.URI;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class DocumentClient {

    private final String serverUrl;
    private final RestTemplate restTemplate;

    public DocumentClient(String host, int port, RestTemplate restTemplate){
        this.serverUrl = host + ":" + port + "/documents";
        this.restTemplate = restTemplate;
    }

    public List<Document> findAll(){
        final ResponseEntity<List<Document>> response = restTemplate.exchange(serverUrl, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<>() {
        });

        return response.getBody();
    }

    public Document findByKey(String key){
        final String url = serverUrl + "/" + key;

        final ResponseEntity<Document> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, Document.class);

        return response.getBody();
    }

    public List<Document> findByWord(String word){
        //Yea, I know I can use uriVariables in restTemplate
        final String url = serverUrl + "?word=" + word;

        final ResponseEntity<List<Document>> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<>() {
        });

        return response.getBody();
    }

    public Document save(Document dto){
        final RequestEntity<Document> request = new RequestEntity<>(dto, HttpMethod.POST, URI.create(serverUrl));

        final ResponseEntity<Document> response = restTemplate.exchange(request, Document.class);

        return response.getBody();
    }
}
