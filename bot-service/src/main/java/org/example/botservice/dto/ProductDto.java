package org.example.botservice.dto;

import java.util.UUID;

public record ProductDto(
        String product,
        Long id,
        UUID callback
) {}

