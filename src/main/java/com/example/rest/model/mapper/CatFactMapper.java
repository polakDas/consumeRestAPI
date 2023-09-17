package com.example.rest.model.mapper;

import com.example.rest.model.dto.CatFact;
import com.example.rest.model.dto.CatFactOther;
import com.example.rest.model.dto.TimeStamp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class CatFactMapper {
    private final ObjectMapper objectMapper;

    public CatFactMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<CatFact> mapCatFacts(String jsonResponse) {
        List<CatFact> catFacts = new ArrayList<>();
        try {
            List<Object> jsonArray = objectMapper.readValue(jsonResponse, List.class);

            for (Object obj : jsonArray) {
                if (obj instanceof Map) {
                    Map<String, Object> catFactData = (Map<String, Object>) obj;

                    String id = getString(catFactData, "_id");
                    String user = getString(catFactData, "user");
                    String text = getString(catFactData, "text");
                    String source = getString(catFactData, "source");
                    String type = getString(catFactData, "type");
                    Boolean deleted = getBoolean(catFactData, "deleted");
                    Boolean used = getBoolean(catFactData, "used");
                    String updatedAt = getDateTime(catFactData, "updatedAt");
                    String createdAt = getDateTime(catFactData, "createdAt");

                    CatFact catFact = new CatFact();
                    catFact.setContent(text);

                    CatFactOther catFactOther = new CatFactOther();
                    catFactOther.setId(id);
                    catFactOther.setUser(user);
                    catFactOther.setSource(source);
                    catFactOther.setType(type);
                    catFactOther.setDeleted(deleted != null ? deleted : false);
                    catFactOther.setUsed(used != null ? used : false);

                    catFact.setOther_data(catFactOther);

                    TimeStamp timeStamp = new TimeStamp();
                    timeStamp.setUpdatedAt(updatedAt);
                    timeStamp.setCreatedAt(createdAt);

                    catFact.setTime_stamp(timeStamp);

                    catFacts.add(catFact);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return catFacts;
    }

    private String getString(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value instanceof String ? (String) value : null;
    }

    private Boolean getBoolean(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value instanceof Boolean ? (Boolean) value : null;
    }

    private String getDateTime(Map<String, Object> map, String key) {
        String isoTimestamp = (String) map.get(key);
        Instant instant = Instant.parse(isoTimestamp);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy - hh:mma");

        return zonedDateTime.format(formatter);
    }
}
