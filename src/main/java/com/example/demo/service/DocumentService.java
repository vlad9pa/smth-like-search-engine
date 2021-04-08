package com.example.demo.service;

import com.example.demo.entity.Document;
import java.util.List;

public interface DocumentService {

    Document save(Document dto);

    List<Document> findAll();

    Document findByKey(String key);

    List<Document> findByWord(String word);

}
