package com.lab.url_shortener.repository;

import com.lab.url_shortener.dto.ShortenedUrlStatisticsDTO;
import com.lab.url_shortener.model.ShortenedUrlAccess;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ShortenedUrlAccessRepository {

    private final JdbcClient jdbcClient;

    public ShortenedUrlAccessRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<ShortenedUrlStatisticsDTO> getStatistics(String shortCode) {
        return jdbcClient
                .sql("""
                    SELECT su.id,
                           su.url,
                           su.short_code,
                           su.created_at,
                           su.updated_at,
                           sa.access_count
                    FROM
                        SHORTENED_URL su
                    JOIN SHORTENED_URL_ACCESS sa
                    ON su.id = sa.id
                    WHERE su.short_code = :shortCode
                """)
                .param("shortCode", shortCode)
                .query(ShortenedUrlStatisticsDTO.class)
                .optional();
    }

    public Integer insertStatistics(Integer id, Integer accessCount) {
        return jdbcClient
                .sql("""
                    INSERT INTO shortened_url_access (id, access_count) 
                    VALUES (:id, :accessCount)
                """)
                .param("id", id)
                .param("accessCount", accessCount)
                .update();
    }

    public Integer updateStatistics(Integer id, Integer accessCount) {
        return jdbcClient
                .sql("""
                    UPDATE SHORTENED_URL_ACCESS SET ACCESS_COUNT = :accessCount
                    WHERE id = :id
                """)
                .param("accessCount", accessCount)
                .param("id", id)
                .update();
    }

    public Optional<ShortenedUrlAccess> findById(Integer id) {
        return jdbcClient
                .sql("""
                    SELECT id, access_count
                    FROM
                    SHORTENED_URL_ACCESS
                    WHERE
                    ID = :id
                """)
                .param("id", id)
                .query(ShortenedUrlAccess.class)
                .optional();
    }
}
