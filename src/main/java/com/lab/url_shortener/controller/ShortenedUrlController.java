package com.lab.url_shortener.controller;

import com.lab.url_shortener.dto.ShortenedUrlStatisticsDTO;
import com.lab.url_shortener.dto.UrlRequestDTO;
import com.lab.url_shortener.model.ShortenedUrl;
import com.lab.url_shortener.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shorten")
public class ShortenedUrlController {

    private final UrlShortenerService urlShortenerService;

    public ShortenedUrlController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping
    public ResponseEntity<?> createShortenedUrl(@RequestBody UrlRequestDTO url) {
        this.urlShortenerService.createShortenedUrl(url.url());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<ShortenedUrl> retrieveShortenedUrl(@PathVariable String shortCode) {
        var result = this.urlShortenerService.getShortenedUrlByShortCode(shortCode);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{shortCode}")
    public ResponseEntity<ShortenedUrl> updateShortenedUrl(@PathVariable String shortCode, @RequestBody UrlRequestDTO url) {
        var result = this.urlShortenerService.updateUrlByShortCode(shortCode, url);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<?> deleteShortenedUrl(@PathVariable String shortCode) {
        this.urlShortenerService.deleteShortenedUrlByShortCode(shortCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<ShortenedUrlStatisticsDTO> retrieveStatistics(@PathVariable String shortCode) {
        var result = this.urlShortenerService.retrieveShortenedUrlStatistics(shortCode);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
