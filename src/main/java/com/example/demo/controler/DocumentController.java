package com.example.demo.controler;

import com.example.demo.entity.Document;
import com.example.demo.service.DocumentService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService service;

    @GetMapping
    public List<Document> findAll(){
        return service.findAll();
    }

    @GetMapping("/{key}")
    public Document findByKey(@PathVariable String key){
        return service.findByKey(key);
    }

    @GetMapping(params = {"word"})
    public List<Document> findByWord(@RequestParam String word){
        return service.findByWord(word);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Document save(@RequestBody Document body){
        return service.save(body);
    }
}
