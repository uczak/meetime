package com.test.meetime.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HubspotContactProperties {

    @JsonProperty("createdate")
    private String createdDate;

    @JsonProperty("email")
    private String email;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("hs_object_id")
    private String hsObjectId;

    @JsonProperty("lastmodifieddate")
    private String lastModifiedDate;

    @JsonProperty("lastname")
    private String lastname;
}