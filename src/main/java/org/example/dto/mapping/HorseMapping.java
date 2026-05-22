package org.example.dto.mapping;

import org.example.dto.HorseGroomingRequest;
import org.example.dto.HorseRequest;
import org.example.dto.HorseResponse;
import org.example.model.Horse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class HorseMapping {

    public abstract Horse toEntity(HorseRequest horseRequest);


    public abstract HorseResponse toResponse(Horse horse);

    public abstract HorseGroomingRequest toRequestForGrooming(Horse horse);
}
