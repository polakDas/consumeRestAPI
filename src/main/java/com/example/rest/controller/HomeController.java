package com.example.rest.controller;


import com.example.rest.model.dto.CatFact;
import com.example.rest.service.CatFactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {
    private final CatFactService catFactService;

    @Autowired
    public HomeController(CatFactService catFactService) {
        this.catFactService = catFactService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CatFact>> getData() {
        return new ResponseEntity<>(catFactService.consumeAPI(), HttpStatus.OK);
    }
}
