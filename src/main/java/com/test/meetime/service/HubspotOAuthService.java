package com.test.meetime.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.meetime.client.HubSpotAuthClient;
import com.test.meetime.excption.ExpiredAuthorizationCodeException;
import com.test.meetime.excption.OAuthIntegrationException;
import com.test.meetime.model.HubspotTokenResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Slf4j
@Service
public class HubspotOAuthService {

    @Autowired
    private HubSpotAuthClient hubSpotAuthClient;

    @Autowired
    private HubspotTokenService hubSpotTokenService;

    @Value("${hubspot.client-id}")
    private String clientId;
    @Value("${hubspot.client-secret}")
    private String clientSecret;
    @Value("${hubspot.redirect-uri}")
    private String redirectUri;
    @Value("${hubspot.scope}")
    private String scope;

    public String generateAuthorizationUrl() {

        String urlAuthorize = "https://app.hubspot.com/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8);
        ;
        return  urlAuthorize;
    }

    public ResponseEntity<HubspotTokenResponse> exchangeAuthorizationCode(String code) {
        try {

            HubspotTokenResponse tokenResponse = hubSpotAuthClient.getToken(
                    "authorization_code",
                    clientId,
                    clientSecret,
                    redirectUri,
                    code
            );

            hubSpotTokenService.storeAccessToken(tokenResponse);//save token cache

            return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
        } catch (FeignException e) {
            String content = e.contentUTF8();
            log.error("Feign error response from HubSpot: {}", content);

            // extract status
            String status = null;
            String message = null;

            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode errorJson = mapper.readTree(content);
                status = errorJson.path("status").asText();
                message = errorJson.path("message").asText();
            } catch (Exception parseEx) {
                log.warn("Failed to parse error JSON from HubSpot", parseEx);
            }

            if ("EXPIRED_AUTH_CODE".equals(status)) {
                throw new ExpiredAuthorizationCodeException(message != null ? message : "Authorization code expired.");
            }
            throw new OAuthIntegrationException("HubSpot integration error.", e);
        }
    }
}