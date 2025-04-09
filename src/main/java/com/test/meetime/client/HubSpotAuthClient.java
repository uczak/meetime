package com.test.meetime.client;

import com.test.meetime.model.HubspotTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hubspotAuthClient", url = "${hubspot.url}")
public interface HubSpotAuthClient {

    @PostMapping(value = "/oauth/v1/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    HubspotTokenResponse getToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code
    );

}
