package com.test.meetime.service;

import com.test.meetime.DTO.PropertiesDTO;
import com.test.meetime.client.HubSpotContactClient;
import com.test.meetime.excption.ExpiredAccessTokenException;
import com.test.meetime.fixture.*;
import com.test.meetime.model.*;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HubspotContactServiceTest {


    @InjectMocks
    private HubspotContactService hubspotContactService;

    @Mock
    private HubSpotContactClient hubSpotContactClient;

    @Test
    void getContactsWithSuccess() {

        HubspotTokenResponse hubspotTokenResponse = HubspotTokenResponseFixture.build();
        HubspotContactsListResponse hubspotContactsListResponse = HubspotContactsListResponseFixture.build();
        ResponseEntity excpected = ResponseEntity.status(HttpStatus.OK).body(hubspotContactsListResponse);

        when(hubSpotContactClient.getContacts(any(), any(), any()))
                .thenReturn(hubspotContactsListResponse);

        ResponseEntity<HubspotContactsListResponse> actual =
                hubspotContactService.getContacts(hubspotTokenResponse.getAccessToken(), 1);

        assertEquals(excpected, actual);
        verify(hubSpotContactClient).getContacts(any(), any(), any());

    }

    @Test
    void getContactsWithErrorAuth() {

        HubspotTokenResponse hubspotTokenResponse = HubspotTokenResponseFixture.build();

       // HubspotErrorResponse hubspotErrorResponse = new HubspotErrorResponse(401,"Unauthorized","message","timestamp");

       // ResponseEntity excpected = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(hubspotErrorResponse);

        // Criando uma FeignException simulando 401 UNAUTHORIZED
        FeignException unauthorizedException = new FeignException.Unauthorized(
                "Unauthorized",
                Request.create(Request.HttpMethod.GET, "/contacts", Map.of(), null, new RequestTemplate()),
                null,
                null
        );

        when(hubSpotContactClient.getContacts(any(), any(), any()))
                .thenThrow(unauthorizedException);

        assertThrows(ExpiredAccessTokenException.class, () -> {
            hubspotContactService.getContacts(hubspotTokenResponse.getAccessToken(), 1);
        });

        verify(hubSpotContactClient).getContacts(any(), any(), any());

    }

    @Test
    void createContactWithSuccess() {

        HubspotTokenResponse hubspotTokenResponse = HubspotTokenResponseFixture.build();
        HubspotContactCreateResponse hubspotContactCreateResponse = HubspotContactCreateResponseFixture.build();
        PropertiesDTO propertiesDTO = PropertiesDTOFixture.build();
        ResponseEntity excpected = ResponseEntity.status(HttpStatus.OK).body(hubspotContactCreateResponse);

        when(hubSpotContactClient.createContact(any(), any()))
                .thenReturn(hubspotContactCreateResponse);

        ResponseEntity<HubspotContactCreateResponse> actual =
                hubspotContactService.createContact(hubspotTokenResponse.getAccessToken(), propertiesDTO);

        assertEquals(excpected, actual);
        verify(hubSpotContactClient).createContact(any(), any());

    }

    @Test
    void createContactWithErrorAuth() {

        HubspotTokenResponse hubspotTokenResponse = HubspotTokenResponseFixture.build();
        PropertiesDTO propertiesDTO = PropertiesDTOFixture.build();
      //  HubspotErrorResponse hubspotErrorResponse = new HubspotErrorResponse(401,"Unauthorized","message","timestamp");

       // ResponseEntity excpected = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(hubspotErrorResponse);

        // Criando uma FeignException simulando 401 UNAUTHORIZED
        FeignException unauthorizedException = new FeignException.Unauthorized(
                "Unauthorized",
                Request.create(Request.HttpMethod.GET, "/contacts", Map.of(), null, new RequestTemplate()),
                null,
                null
        );

        when(hubSpotContactClient.createContact(any(), any()))
                .thenThrow(unauthorizedException);

        assertThrows(ExpiredAccessTokenException.class, () -> {
            hubspotContactService.createContact(hubspotTokenResponse.getAccessToken(), propertiesDTO);
        });

        verify(hubSpotContactClient).createContact(any(), any());

    }

}