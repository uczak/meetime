package com.test.meetime.fixture;

import com.test.meetime.model.HubspotContactProperties;

public class HubspotContactPropertiesFixture {

    private static HubspotContactProperties buildRandom() {
        HubspotContactProperties hubspotContactProperties = new HubspotContactProperties();
        hubspotContactProperties.setCreatedDate("Date");
        hubspotContactProperties.setFirstname("Guilherme");
        hubspotContactProperties.setEmail("guilherme.uczak@gmail.com");
        hubspotContactProperties.setLastname("Uczak");
        hubspotContactProperties.setLastModifiedDate("Date");
        return hubspotContactProperties;

    }

    public static HubspotContactProperties build() {
        return buildRandom();
    }

}
