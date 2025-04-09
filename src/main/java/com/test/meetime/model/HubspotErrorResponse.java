package com.test.meetime.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a standard error response for API endpoints.")
public class HubspotErrorResponse {

    @Schema(description = "HTTP status code")
    private int status;

    @Schema(description = "Short description of the error")
    private String error;

    @Schema(description = "Detailed message about the error")
    private String message;

    @Schema(description = "Timestamp when the error occurred")
    private String timestamp;

}
