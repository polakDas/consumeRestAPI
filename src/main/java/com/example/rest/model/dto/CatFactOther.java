package com.example.rest.model.dto;

import lombok.Data;

@Data
public class CatFactOther {
    private String id;
    private String user;
    private String source;
    private String type;
    private boolean deleted;
    private boolean used;
}
