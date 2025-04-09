package com.test.meetime.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HubspotContact {

    @JsonProperty("id")
    private String id;

    @JsonProperty("properties")
    private HubspotContactProperties properties;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("archived")
    private boolean archived;
}
