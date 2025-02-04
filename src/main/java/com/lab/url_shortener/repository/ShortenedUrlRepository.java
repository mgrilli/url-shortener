package com.lab.url_shortener.repository;

import com.lab.url_shortener.model.ShortenedUrl;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class ShortenedUrlRepository {

    private final JdbcClient jdbcClient;

    public ShortenedUrlRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Integer create(String url, String shortCode, LocalDateTime createdAt) {
        return jdbcClient
                .sql("INSERT INTO SHORTENED_URL (url, short_code, created_at) VALUES (:url, :shortCode, :createdAt)")
                .param("url", url)
                .param("shortCode", shortCode)
                .param("createdAt", createdAt)
                .update();
    }

    public Optional<ShortenedUrl> findByShortCode(String shortCode) {
        return jdbcClient
                .sql("SELECT id, url, short_code, created_at, updated_at " +
                     "FROM SHORTENED_URL " +
                     "WHERE short_code = :shortCode")
                .param("shortCode", shortCode)
                .query(ShortenedUrl.class)
                .optional();
    }

    public Integer updateUrlByShortCode(String shortCode, String url, LocalDateTime updatedAt) {
        return jdbcClient
                .sql("UPDATE SHORTENED_URL SET url = :url, updated_at = :updatedAt" +
                       " WHERE short_code = :shortCode")
                .param("url", url)
                .param("updatedAt", updatedAt)
                .param("shortCode", shortCode)
                .update();
    }

    public Integer deleteByShortCode(String shortCode) {
        return jdbcClient
                .sql("DELETE FROM SHORTENED_URL WHERE short_code = :shortCode")
                .param("shortCode", shortCode)
                .update();
    }
}
