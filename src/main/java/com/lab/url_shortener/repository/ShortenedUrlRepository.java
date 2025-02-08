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

    public ShortenedUrl create(String url, String shortCode) {
        return jdbcClient
                .sql("""
                     INSERT INTO shortened_url (url, short_code) VALUES(:url, :shortCode)
                     RETURNING id, url, short_code, created_at, updated_at
                 """)
                .param("url", url)
                .param("shortCode", shortCode)
                .query(ShortenedUrl.class)
                .single();
    }

    public Optional<ShortenedUrl> findByShortCode(String shortCode) {
        return jdbcClient
                .sql("""
                    SELECT id, url, short_code, created_at, updated_at
                    FROM shortened_url
                    WHERE short_code = :shortCode
                """)
                .param("shortCode", shortCode)
                .query(ShortenedUrl.class)
                .optional();
    }

    public Integer updateUrlByShortCode(String shortCode, String url, LocalDateTime updatedAt) {
        return jdbcClient
                .sql("""
                    UPDATE shortened_url SET url = :url, updated_at = :updatedAt
                    WHERE short_code = :shortCode
                """)
                .param("url", url)
                .param("updatedAt", updatedAt)
                .param("shortCode", shortCode)
                .update();
    }

    public Integer deleteByShortCode(String shortCode) {
        return jdbcClient
                .sql("""
                    DELETE FROM shortened_url 
                    WHERE short_code = :shortCode
                """)
                .param("shortCode", shortCode)
                .update();
    }

}
