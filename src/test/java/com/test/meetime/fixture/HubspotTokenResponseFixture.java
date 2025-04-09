package com.test.meetime.fixture;

import com.test.meetime.model.HubspotTokenResponse;

import java.util.Date;

public class HubspotTokenResponseFixture {
    private static HubspotTokenResponse buildRandom() {
        HubspotTokenResponse hubspotTokenResponse = new HubspotTokenResponse();
        hubspotTokenResponse.setAccessToken("access_token");
        hubspotTokenResponse.setRefreshToken("refresh_token");
        hubspotTokenResponse.setTokenType("Bearer");
        hubspotTokenResponse.setScope(null);
        hubspotTokenResponse.setExpiresIn(1800);
        return hubspotTokenResponse;

    }

    public static HubspotTokenResponse build() {
        return buildRandom();
    }

}
