package com.test.meetime.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@Validated
@RestController
@RequestMapping("/webhook")
@Tag(name = "Webhook", description = "Receives events sent from HubSpot.")
public class WebhookController {

    @Operation(
            summary = "Receive HubSpot Webhook Events",
            description = "This endpoint is triggered by HubSpot when a configured event occurs.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Webhook received successfully."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request payload."
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Void> receiveWebhook(@RequestBody String payload,
                                               @RequestHeader Map<String, String> headers) {
        log.info("Webhook request:");
        log.info("Headers: " + headers);
        log.info("Payload: " + payload);
        return ResponseEntity.ok().build();
    }
}
