package com.example.rest.service;

import com.example.rest.model.dto.CatFact;
import com.example.rest.model.mapper.CatFactMapper;
import com.example.rest.utils.RestConstant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatFactService {
    private final RestTemplate restTemplate;
    private final CatFactMapper catFactMapper;

    @Autowired
    public CatFactService(RestTemplate restTemplate, CatFactMapper catFactMapper) {
        this.restTemplate = restTemplate;
        this.catFactMapper = catFactMapper;
    }

    public List<CatFact> consumeAPI() {
        String jsonResponse = restTemplate.getForObject(RestConstant.URL, String.class);
        return catFactMapper.mapCatFacts(jsonResponse).stream().limit(10).collect(Collectors.toList());
    }
}
