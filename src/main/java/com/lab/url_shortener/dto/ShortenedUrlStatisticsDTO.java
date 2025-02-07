package com.lab.url_shortener.dto;

import java.time.LocalDateTime;

public record ShortenedUrlStatisticsDTO(Integer id, String url, String shortCode, LocalDateTime createdAt, LocalDateTime updatedAt, Integer accessCount) {
}
