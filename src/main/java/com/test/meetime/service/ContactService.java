package com.test.meetime.service;

import com.test.meetime.DTO.PropertiesDTO;
import com.test.meetime.client.HubSpotContactClient;
import com.test.meetime.excption.ExpiredAccessTokenException;
import com.test.meetime.excption.OAuthIntegrationException;
import com.test.meetime.model.HubspotContactCreateRequest;
import com.test.meetime.model.HubspotContactCreateResponse;
import com.test.meetime.model.HubspotContactsListResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ContactService {

    @Autowired
    private HubSpotContactClient hubSpotContactClient;


    public ResponseEntity<HubspotContactsListResponse> getContacts(String accessToken, Integer count) {
        try {
            HubspotContactsListResponse hubspotContactsListResponse = hubSpotContactClient.getContacts("Bearer " + accessToken, count, false);
            return ResponseEntity.status(HttpStatus.OK).body(hubspotContactsListResponse);
        } catch (FeignException e) {
            String content = e.contentUTF8();
            log.error("Feign error from HubSpot: {}", content);
            if (HttpStatus.UNAUTHORIZED.value() == e.status()) {
                throw new ExpiredAccessTokenException("Access token expired. Please re-authenticate.");
            }
            throw new OAuthIntegrationException("HubSpot integration error when retrieving contacts.", e);
        }
    }

    public ResponseEntity<HubspotContactCreateResponse> createContact(String accessToken, PropertiesDTO propertiesDTO) {
        try {
            HubspotContactCreateRequest request = new HubspotContactCreateRequest(propertiesDTO);
            HubspotContactCreateResponse hubspotContactCreateResponse = hubSpotContactClient.createContact("Bearer " + accessToken, request);
            return ResponseEntity.status(HttpStatus.OK).body(hubspotContactCreateResponse);
        } catch (FeignException e) {
            String content = e.contentUTF8();
            log.error("Feign error from HubSpot: {}", content);
            if (HttpStatus.UNAUTHORIZED.value() == e.status()) {
                throw new ExpiredAccessTokenException("Access token expired. Please re-authenticate.");
            }
            throw new OAuthIntegrationException("HubSpot integration error when retrieving contacts.", e);
        }
    }
}
