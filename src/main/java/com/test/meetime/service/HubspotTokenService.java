package com.test.meetime.service;

import com.test.meetime.excption.InvalidAccessTokenException;
import com.test.meetime.model.HubspotTokenResponse;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class HubspotTokenService {

    @CachePut(value = "hubspotTokenCache", key = "'access_token'")
    public HubspotTokenResponse storeAccessToken(HubspotTokenResponse token) {
        return token;
    }

    @Cacheable(value = "hubspotTokenCache", key = "'access_token'")
    public HubspotTokenResponse getAccessToken() {
        throw new InvalidAccessTokenException("Access token not found. Please re-authenticate.");
    }

}
