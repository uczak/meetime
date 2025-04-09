package com.test.meetime.fixture;

import com.test.meetime.model.HubspotContact;

public class HubspotContactFixture {

    private static HubspotContact buildRandom() {
        HubspotContact hubspotContact = new HubspotContact();
        hubspotContact.setId("1");
        hubspotContact.setArchived(false);
        hubspotContact.setProperties(HubspotContactPropertiesFixture.build());
        hubspotContact.setCreatedAt("CreateAt");
        hubspotContact.setUpdatedAt("UpdateAt");
        return hubspotContact;

    }

    public static HubspotContact build() {
        return buildRandom();
    }

}
