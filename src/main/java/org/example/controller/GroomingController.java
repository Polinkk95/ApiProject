package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.enums.Grooming;
import org.example.service.GroomingService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/grooming")
public class GroomingController {

    private final GroomingService groomingService;

    @PostMapping("/{horseId}")
    public void sendToGrooming(
            @PathVariable Long horseId,
            @RequestParam List<Grooming> services) {
        groomingService.sendToGrooming(horseId, services);
    }
}
