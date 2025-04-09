package com.test.meetime.client;

import com.test.meetime.model.HubspotContactCreateRequest;
import com.test.meetime.model.HubspotContactCreateResponse;
import com.test.meetime.model.HubspotContactsListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "hubspotContactClient", url = "${hubspot.url}")
public interface HubSpotContactClient {

    @GetMapping(value = "/crm/v3/objects/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
    HubspotContactsListResponse getContacts(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("limit") Integer limit,
            @RequestParam("archived") Boolean archived
    );

    @PostMapping(value = "/crm/v3/objects/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
    HubspotContactCreateResponse createContact(
            @RequestHeader("Authorization") String authorization,
            @RequestBody HubspotContactCreateRequest properties
    );
}
