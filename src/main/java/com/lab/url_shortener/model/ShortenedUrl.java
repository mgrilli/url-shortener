package com.lab.url_shortener.model;

import java.time.LocalDateTime;

public record ShortenedUrl(Integer id, String url, String shortCode, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
