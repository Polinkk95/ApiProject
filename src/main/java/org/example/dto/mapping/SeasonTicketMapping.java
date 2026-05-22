package org.example.dto.mapping;

import org.example.dto.SeasonTicketResponse;
import org.example.model.SeasonTicket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class SeasonTicketMapping {

    public abstract SeasonTicketResponse toResponse(SeasonTicket seasonTicket);
}
