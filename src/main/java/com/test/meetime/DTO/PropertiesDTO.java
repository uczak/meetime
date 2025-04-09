package com.test.meetime.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PropertiesDTO {

    @JsonProperty("email")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email must be at most 255 characters long")
    private String email;

    @JsonProperty("lastname")
    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must be at most 100 characters long")
    private String lastname;

    @JsonProperty("firstname")
    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must be at most 100 characters long")
    private String firstname;

}
