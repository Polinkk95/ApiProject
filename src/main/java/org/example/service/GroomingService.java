package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.client.GroomingClient;
import org.example.enums.Grooming;
import org.example.dto.HorseGroomingRequest;
import org.example.dto.mapping.HorseMapping;
import org.example.model.Horse;
import org.example.repository.HorseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroomingService{

    private final GroomingClient groomingClient;

    private final HorseRepository horseRepository;

    private final HorseMapping horseMapping;

    public void sendToGrooming(Long horseId, List<Grooming> services) {
        Horse horse = horseRepository.findById(horseId)
                .orElseThrow(()->new IllegalArgumentException("Horse with id "+horseId+" not found"));
        HorseGroomingRequest horseRequest = horseMapping.toRequestForGrooming(horse);
        horseRequest.setServices(services);
        groomingClient.sendToGrooming(horseRequest);
    }
}
