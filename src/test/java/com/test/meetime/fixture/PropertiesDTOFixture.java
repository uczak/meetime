package com.test.meetime.fixture;

import com.test.meetime.DTO.PropertiesDTO;

public class PropertiesDTOFixture {

    private static PropertiesDTO buildRandom() {
        PropertiesDTO propertiesDTO = new PropertiesDTO();
        propertiesDTO.setEmail("guilherme.uczak@gmail.com");
        propertiesDTO.setFirstname("Guilherme");
        propertiesDTO.setLastname("Uczak");
        return propertiesDTO;

    }

    public static PropertiesDTO build() {
        return buildRandom();
    }

}
