
package com.example.fi.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;


@Data
public class FiEventRequest {
    @NotBlank
    @Size(max = 100)
    private String eventId;

    @NotBlank
    @Size(max = 50)
    private String eventType;

    @NotBlank
    @Size(max = 50)
    private String fiId;



}
