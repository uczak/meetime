package com.test.meetime.fixture;

import com.test.meetime.model.HubspotContactsListResponse;

import java.util.List;

public class HubspotContactsListResponseFixture {

    private static HubspotContactsListResponse buildRandom() {
        HubspotContactsListResponse hubspotContactsListResponse = new HubspotContactsListResponse();
        hubspotContactsListResponse.setResults(List.of(HubspotContactFixture.build()));
        return hubspotContactsListResponse;

    }

    public static HubspotContactsListResponse build() {
        return buildRandom();
    }

}
