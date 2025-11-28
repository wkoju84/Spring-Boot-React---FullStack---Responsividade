package br.com.william.planner.controllers.dto;

import java.time.LocalDateTime;

public record RecordDTO(
        Long id,
        String service,
        String customer,
        String location,
        LocalDateTime dateTime,
        boolean done,
        boolean canceled
) {}
