package com.test.meetime.model;

import com.test.meetime.DTO.PropertiesDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HubspotContactCreateRequest {
    private Properties properties;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Properties {
        private String email;
        private String firstname;
        private String lastname;
    }

    public HubspotContactCreateRequest(PropertiesDTO propertiesDTO) {
        Properties properties = new Properties();
        properties.setEmail(propertiesDTO.getEmail());
        properties.setFirstname(propertiesDTO.getFirstname());
        properties.setLastname(propertiesDTO.getLastname());
        this.properties = properties;
    }
}