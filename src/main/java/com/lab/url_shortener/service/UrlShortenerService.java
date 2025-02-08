package com.lab.url_shortener.service;

import com.lab.url_shortener.dto.ShortenedUrlStatisticsDTO;
import com.lab.url_shortener.dto.UrlRequestDTO;
import com.lab.url_shortener.exception.InvalidUrlException;
import com.lab.url_shortener.exception.ShortCodeNotFoundException;
import com.lab.url_shortener.model.ShortenedUrl;
import com.lab.url_shortener.repository.ShortenedUrlAccessRepository;
import com.lab.url_shortener.repository.ShortenedUrlRepository;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UrlShortenerService {

    private final ShortenedUrlRepository repository;
    private final ShortenedUrlAccessRepository statsRepository;

    public UrlShortenerService(ShortenedUrlRepository repository, ShortenedUrlAccessRepository statsRepository) {
        this.repository = repository;
        this.statsRepository = statsRepository;
    }

    public ShortenedUrl createShortenedUrl(String url) {
        if (isValidUrl(url)) {
            String shortCode = shortCodeGenerator();
            return this.repository.create(url, shortCode);
        } else {
            throw new InvalidUrlException("Invalid URL");
        }
    }

    public ShortenedUrl getShortenedUrlByShortCode(String shortCode) {
        var shortUrl = this.repository.findByShortCode(shortCode);
        if (shortUrl.isPresent()) {
            Integer id = shortUrl.get().id();
            var stat = this.statsRepository.findById(id);
            if (stat.isPresent()) {
                Integer counter = stat.get().accessCount();
                counter++;
                this.statsRepository.updateStatistics(id, counter);
            } else {
                this.statsRepository.insertStatistics(id, 1);
            }
            return shortUrl.get();
        } else {
            throw new ShortCodeNotFoundException("Short code not found!");
        }
    }

    public ShortenedUrl updateUrlByShortCode(String shortCode, UrlRequestDTO url) {
        if (isValidUrl(url.url())) {
            LocalDateTime updatedAt = LocalDateTime.now();
            var updated = this.repository.updateUrlByShortCode(shortCode, url.url(), updatedAt);
            if (updated == 0) {
                throw new ShortCodeNotFoundException("Short code not found!");
            }
            var shortenedUrl = this.repository.findByShortCode(shortCode).get();
            return shortenedUrl;
        } else {
            throw new InvalidUrlException("Invalid URL");
        }
    }

    public ShortenedUrlStatisticsDTO retrieveShortenedUrlStatistics(String shortCode) {
        var statistics = this.statsRepository.getStatistics(shortCode);
        if (statistics.isPresent()) {
            return statistics.get();
        } else {
            throw new ShortCodeNotFoundException("Short code not found!");
        }
    }

    public void deleteShortenedUrlByShortCode(String shortCode) {
        var delete = this.repository.deleteByShortCode(shortCode);
        if (delete == 0) {
            throw new ShortCodeNotFoundException("Short code not found!");
        }
    }

    private String shortCodeGenerator() {
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        while (sb.toString().getBytes(StandardCharsets.UTF_8).length < 10) {
            char randomChar = alphanumeric.charAt(random.nextInt(alphanumeric.length()));
            sb.append(randomChar);
        }
        return sb.toString();
    }

    private boolean isValidUrl(String url) {
        try {
            URI uri = new URI(url);
            return uri.getScheme() != null && uri.getHost() != null;
        } catch (Exception e) {
            return false;
        }
    }
}
