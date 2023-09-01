package es.menasoft.cacherequest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.*;

@RestController
@RequestMapping("/cached")
@Slf4j
public class Controller {

    @GetMapping("/expires")
    public ResponseEntity<String> getWithExpires() {
        log.info("Backend called with Expires header");
        HttpHeaders headers = new HttpHeaders();
        headers.setExpires(OffsetDateTime.now().plusDays(1).toInstant());
        return ResponseEntity.ok()
                .headers(headers)
                .body("Response with cache mechanism based on expires header with headers: %s".formatted(headers));
    }

    @GetMapping("/cacheControl")
    public ResponseEntity<String> getWithCacheControl() {
        log.info("Backend called with Cache Control header");
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.maxAge(Duration.ofDays(1)));
        return ResponseEntity.ok()
                .headers(headers)
                .body("Response with cache mechanism based on cache control header with headers %s".formatted(headers));
    }

    @GetMapping("/eTag")
    public ResponseEntity<String> getWithEtag() {
        log.info("Backend called with eTag header");
        HttpHeaders headers = new HttpHeaders();
        Instant instant = LocalDateTime.of(2023, Month.AUGUST, 26, 1, 0, 40).toInstant(ZoneOffset.UTC);
        headers.setETag("\"" + instant.toEpochMilli() + "\"");
        return ResponseEntity.ok()
                .headers(headers)
                .body("Response with cache mechanism based on ETag header with headers %s".formatted(headers));
    }

    @GetMapping("/lastModified")
    public ResponseEntity<String> getWithLastModified() {
        log.info("Backend called with Last Modified header");
        HttpHeaders headers = new HttpHeaders();
        headers.setLastModified(LocalDateTime.of(2023, Month.AUGUST, 26, 1, 0, 40).toInstant(ZoneOffset.UTC));
        return ResponseEntity.ok()
                .headers(headers)
                .body("Response with cache mechanism based on Last Modified header with headers %s".formatted(headers));
    }
}
