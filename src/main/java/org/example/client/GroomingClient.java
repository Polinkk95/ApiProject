package org.example.client;

import org.example.dto.HorseGroomingRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "grooming-service", url = "${services.grooming.url}")
public interface GroomingClient {

    @PostMapping("/api/horses/receive")
    void sendToGrooming(
            @RequestBody HorseGroomingRequest horse);
}
