package com.test.meetime.fixture;

import com.test.meetime.model.HubspotContactCreateResponse;

public class HubspotContactCreateResponseFixture {

    private static HubspotContactCreateResponse buildRandom() {
        HubspotContactCreateResponse hubspotContactCreateResponse = new HubspotContactCreateResponse();
        hubspotContactCreateResponse.setId("ID");
        hubspotContactCreateResponse.setCreatedAt("CreateAt");
        hubspotContactCreateResponse.setArchived(false);
        hubspotContactCreateResponse.setUpdatedAt("UpdateAt");
        return hubspotContactCreateResponse;

    }

    public static HubspotContactCreateResponse build() {
        return buildRandom();
    }

}
