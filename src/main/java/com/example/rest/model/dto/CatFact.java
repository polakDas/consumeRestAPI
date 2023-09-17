package com.example.rest.model.dto;

import lombok.Data;

@Data
public class CatFact {
    private String content;
    private CatFactOther other_data;
    private TimeStamp time_stamp;
}

