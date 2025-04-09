package com.test.meetime.controller;

import com.test.meetime.DTO.PropertiesDTO;
import com.test.meetime.model.*;
import com.test.meetime.service.ContactService;
import com.test.meetime.service.HubSpotTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/contact")
@Tag(name = "Contact", description = "Endpoints responsible for the integration with Contact objects.")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private HubSpotTokenService hubSpotTokenService;

    @Operation(
            summary = "Retrieve a list of HubSpot contacts.",
            description = "Fetches a list of contacts from HubSpot with the specified count."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Contacts retrieved successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HubspotContact.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized. The access token has expired or is invalid.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HubspotErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error during token exchange.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HubspotErrorResponse.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<HubspotContactsListResponse> getContacts(
            @RequestParam("count")
            @NotNull
            @Min(value = 1, message = "The minimum value for 'count' is 1.")
            @Max(value = 100, message = "The maximum value for 'count' is 100.")
            Integer count) {

        HubspotTokenResponse accessToken = hubSpotTokenService.getAccessToken();
        return contactService.getContacts(accessToken.getAccessToken(), count);
    }

    @Operation(
            summary = "Create a new HubSpot contact",
            description = "Creates a new contact in HubSpot using the provided contact information."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Contact successfully created.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HubspotContactCreateResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input provided.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HubspotErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized. The access token has expired or is invalid.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HubspotErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error during token exchange.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HubspotErrorResponse.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<HubspotContactCreateResponse> createContact(
            @Valid @RequestBody PropertiesDTO contactDTO) {

        HubspotTokenResponse accessToken = hubSpotTokenService.getAccessToken();
        return contactService.createContact(accessToken.getAccessToken(), contactDTO);
    }

}
