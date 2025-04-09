package com.test.meetime.controller;

import com.test.meetime.DTO.PropertiesDTO;
import com.test.meetime.fixture.HubspotContactCreateResponseFixture;
import com.test.meetime.fixture.HubspotContactsListResponseFixture;
import com.test.meetime.fixture.HubspotTokenResponseFixture;
import com.test.meetime.fixture.PropertiesDTOFixture;
import com.test.meetime.model.HubspotContactCreateResponse;
import com.test.meetime.model.HubspotContactsListResponse;
import com.test.meetime.model.HubspotTokenResponse;
import com.test.meetime.service.HubspotContactService;
import com.test.meetime.service.HubspotTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HubspotContactControllerTest {

    @InjectMocks
    private HubspotContactController hubspotContactController;

    @Mock
    private HubspotContactService hubspotContactService;

    @Mock
    private HubspotTokenService hubspotTokenService;


    @Test
    void getContactsWithSuccessController() {

        try {
            HubspotTokenResponse hubspotTokenResponse = HubspotTokenResponseFixture.build();
            HubspotContactsListResponse hubspotContactsListResponse = HubspotContactsListResponseFixture.build();
            ResponseEntity excpected = ResponseEntity.status(HttpStatus.OK).body(hubspotContactsListResponse);


            when(hubspotTokenService.getAccessToken()).thenReturn(hubspotTokenResponse);
            when(hubspotContactService.getContacts(hubspotTokenResponse.getAccessToken(),1)).thenReturn(excpected);

            ResponseEntity actual = hubspotContactController.getContacts(1);

            assertEquals(excpected, actual);
            verify(hubspotContactService).getContacts(hubspotTokenResponse.getAccessToken(),1);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    @Test
    void createContactWithSuccessController() {

        try {
            HubspotTokenResponse hubspotTokenResponse = HubspotTokenResponseFixture.build();
            PropertiesDTO propertiesDTO = PropertiesDTOFixture.build();
            HubspotContactCreateResponse hubspotContactCreateResponse = HubspotContactCreateResponseFixture.build();
            ResponseEntity excpected = ResponseEntity.status(HttpStatus.OK).body(hubspotContactCreateResponse);

            when(hubspotTokenService.getAccessToken()).thenReturn(hubspotTokenResponse);
            when(hubspotContactService.createContact(hubspotTokenResponse.getAccessToken(),propertiesDTO)).thenReturn(excpected);

            ResponseEntity actual = hubspotContactController.createContact(propertiesDTO);

            assertEquals(excpected, actual);
            verify(hubspotContactService).createContact(hubspotTokenResponse.getAccessToken(),propertiesDTO);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}