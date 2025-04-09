package com.test.meetime.controller;

import com.test.meetime.model.HubspotErrorResponse;
import com.test.meetime.model.HubspotTokenResponse;
import com.test.meetime.service.OAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;

@RestController
@RequestMapping("/oauth")
@Tag(name = "Authorization", description = "Endpoints responsible for the OAuth 2.0 authentication flow with HubSpot." +
        "Includes the generation of the authorization URL and the handling of the callback to exchange the code for an access token.")
public class OAuthController {

    @Autowired
    private OAuthService oAuthService;

    @Operation(
            summary = "Generates the HubSpot authorization URL to start the OAuth 2.0 flow.",
            description = "Returns a URL that the client must access to authorize the application in HubSpot."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Authorization URL generated successfully.",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "https://app.hubspot.com/oauth/authorize?client_id=...")
                    )
            )
    })
    @RequestMapping(value = "/authorize-url",
            method = RequestMethod.GET)
    public ResponseEntity<Void> generateAuthorizationUrl() {

        URI authorizationUri = URI.create(oAuthService.generateAuthorizationUrl());
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(authorizationUri)
                .build();

    }

    @Operation(
            summary = "Handles the OAuth callback from HubSpot.",
            description = "Exchanges the authorization code received from HubSpot for an access token."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Access token retrieved successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HubspotTokenResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Authorization code expired or invalid.",
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
    @GetMapping("/callback")
    public RedirectView  exchangeAuthorizationCode(
            @RequestParam("code") String code) {
        oAuthService.exchangeAuthorizationCode(code);
        return new RedirectView("/page.html"); // Vai abrir a página no navegador
    }
}

















  /*  @GetMapping("/callback")
    public ResponseEntity<HubspotTokenResponse> handleCallback(@RequestParam("code") String code) {
        System.out.println(code);
        return oAuthService.handleCallback(code);
    }
*/


//  @Value("${hubspot.client-id}")
// private String clientId = "d90039b5-b654-4caa-8dad-c50d2bcd310b";

// @Value("${hubspot.client-secret}")
// private String clientSecret = "ef84b9cc-ad72-4191-99ee-0e65ee75e50e";

// @Value("${hubspot.redirect-uri}")
// private String redirectUri = "https://google.com";
//Gui#93635001
// private static final String AUTH_URL = "https://app.hubspot.com/oauth/authorize";
// private static final String TOKEN_URL = "https://api.hubapi.com/oauth/v1/token";



    /*    String scope = "contacts";
        String encodedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);
        String url = String.format(
                "%s?client_id=%s&redirect_uri=%s&scope=%s&response_type=code",
                AUTH_URL, clientId, encodedRedirectUri, scope
        );
        ResponseEntity<Map<String, String>> authorizationUrl = ResponseEntity.ok(Map.of("authorizationUrl", url));

        ResponseEntity<String> stringResponseEntity = oAuthService.callHubspotAuth();

        return stringResponseEntity;
*/
/*
 RestTemplate restTemplate = new RestTemplate();

        var requestBody = Map.of(
                "grant_type", "authorization_code",
                "client_id", clientId,
                "client_secret", clientSecret,
                "redirect_uri", redirectUri,
                "code", code
        );

        var response = restTemplate.postForEntity(TOKEN_URL, requestBody, Map.class);

* */