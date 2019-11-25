package com.pavelzezule.spring.jdbc.extensions.model;

import java.time.Instant;

public class TestDto {

    private String dtoName;

    private Instant timestamp;

    public TestDto(String dtoName, Instant timestamp) {
        this.dtoName = dtoName;
        this.timestamp = timestamp;
    }

    public String getDtoName() {
        return dtoName;
    }
    
    public void setDtoName(String dtoName) {
        this.dtoName = dtoName;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
    
}
