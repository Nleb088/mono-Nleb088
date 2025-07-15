package com.interco.rest.entity;

public record TaskDto(
        String id,
        String title,
        long position,
        boolean completed) {
}
