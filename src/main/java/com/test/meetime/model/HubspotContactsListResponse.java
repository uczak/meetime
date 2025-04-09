package com.test.meetime.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class HubspotContactsListResponse {

    @JsonProperty("results")
    private List<HubspotContact> results;
}
