package com.test.meetime.fixture;

import com.test.meetime.model.HubspotContactCreateRequest;

public class HubspotContactCreateRequestFixture {

    private static HubspotContactCreateRequest buildRandom() {
        HubspotContactCreateRequest hubspotContactCreateRequest = new HubspotContactCreateRequest(PropertiesDTOFixture.build());
        return hubspotContactCreateRequest;

    }

    public static HubspotContactCreateRequest build() {
        return buildRandom();
    }

}
