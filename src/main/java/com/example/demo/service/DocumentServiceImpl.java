package com.example.demo.service;

import com.example.demo.entity.Document;
import com.example.demo.repository.DocumentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository repository;


    @Override
    @Transactional
    public Document save(Document dto) {
        return repository.save(dto);
    }

    @Override
    public List<Document> findAll() {
        return repository.findAll();
    }

    @Override
    public Document findByKey(String key) {
        return repository.findByKey(key);
    }

    @Override
    public List<Document> findByWord(String word) {
        return repository.findByWord(word);
    }
}
