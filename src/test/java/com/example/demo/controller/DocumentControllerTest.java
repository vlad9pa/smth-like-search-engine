package com.example.demo.controller;

import com.example.demo.controler.DocumentController;
import com.example.demo.entity.Document;
import com.example.demo.service.DocumentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = {DocumentController.class})
public class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentService service;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @SneakyThrows
    public void shouldSaveDocument(){
        final Document dto = Document.builder()
                .key(UUID.randomUUID().toString())
                .rawDocument("randomStinrg123")
                .build();

        final Document persisted = dto.toBuilder().id(UUID.randomUUID()).build();

        Mockito.when(service.save(any())).thenReturn(persisted);

        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/documents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toBytes(dto));

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").value(persisted.getId().toString()))
                .andExpect(jsonPath("$.key").value(persisted.getKey()))
                .andExpect(jsonPath("$.rawDocument").value(persisted.getRawDocument()));

        Mockito.verify(service, Mockito.times(1)).save(dto);
    }

    @Test
    @SneakyThrows
    public void shouldFindAllDocuments(){

        final Document persisted = Document.builder()
                .id(UUID.randomUUID())
                .key(UUID.randomUUID().toString())
                .rawDocument("randomStinrg123")
                .build();

        Mockito.when(service.findAll()).thenReturn(Collections.singletonList(persisted));

        mockMvc.perform(MockMvcRequestBuilders.get("/documents"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].id").value(persisted.getId().toString()))
                .andExpect(jsonPath("$[0].key").value(persisted.getKey()))
                .andExpect(jsonPath("$[0].rawDocument").value(persisted.getRawDocument()));

        Mockito.verify(service, Mockito.times(1)).findAll();
    }

    @Test
    @SneakyThrows
    public void shouldFindAllDocumentsBySpecificWord(){

        final Document persisted = Document.builder()
                .id(UUID.randomUUID())
                .key(UUID.randomUUID().toString())
                .rawDocument("randomStinrg123")
                .build();

        Mockito.when(service.findByWord(any())).thenReturn(Collections.singletonList(persisted));

        mockMvc.perform(MockMvcRequestBuilders.get("/documents").queryParam("word", "123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].id").value(persisted.getId().toString()))
                .andExpect(jsonPath("$[0].key").value(persisted.getKey()))
                .andExpect(jsonPath("$[0].rawDocument").value(persisted.getRawDocument()));

        Mockito.verify(service, Mockito.times(1)).findByWord("123");
    }

    @Test
    @SneakyThrows
    public void shouldFindAllDocumentsByKey(){

        final Document persisted = Document.builder()
                .id(UUID.randomUUID())
                .key(UUID.randomUUID().toString())
                .rawDocument("randomStinrg123")
                .build();

        Mockito.when(service.findByKey(any())).thenReturn(persisted);

        mockMvc.perform(MockMvcRequestBuilders.get("/documents/" + persisted.getKey()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(persisted.getId().toString()))
                .andExpect(jsonPath("$.key").value(persisted.getKey()))
                .andExpect(jsonPath("$.rawDocument").value(persisted.getRawDocument()));

        Mockito.verify(service, Mockito.times(1)).findByKey(persisted.getKey());
    }

    @SneakyThrows
    private byte[] toBytes(Object object){
        return mapper.writeValueAsBytes(object);
    }

}
