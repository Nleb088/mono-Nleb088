package com.interco.rest.entity;

import java.util.Optional;

public record TaskUpdateDto(
        Optional<String> title,
        Optional<Boolean> completed) {
}
