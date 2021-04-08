package com.example.demo.repository;

import com.example.demo.entity.Document;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {

    @Query(value = "select d from Document d where d.rawDocument like %?1%")
    List<Document> findByWord(String word);

    Document findByKey(String key);

}
