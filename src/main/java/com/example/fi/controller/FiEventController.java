
package com.example.fi.controller;


import com.example.fi.model.FiEventRequest;
import com.example.fi.service.FiEventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fi/events")
public class FiEventController {

    @Autowired
    FiEventService fiEventService;


    @PostMapping
    public ResponseEntity<String> acceptFiEvent(@Valid @RequestBody FiEventRequest request) {
        String result = fiEventService.processEvent(request);
        return ResponseEntity.ok(result);
    }
}
