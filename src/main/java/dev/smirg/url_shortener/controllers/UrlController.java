package dev.smirg.url_shortener.controllers;

import dev.smirg.url_shortener.DTO.CreateUrlRequest;
import dev.smirg.url_shortener.DTO.UrlResponse;
import dev.smirg.url_shortener.entity.UrlEntity;
import dev.smirg.url_shortener.repository.UrlRepository;
import dev.smirg.url_shortener.service.UrlShortenService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlController {

    private final UrlShortenService urlService;
    private final UrlRepository repository;

    public UrlController(UrlRepository repository, UrlShortenService urlService) {
        this.repository = repository;
        this.urlService = urlService;
    }

    @GetMapping("/long_url")
    public Page<UrlEntity> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @GetMapping("/url/{id}")
    public ResponseEntity<UrlEntity> findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/url")
    public ResponseEntity<UrlResponse> createDbEntry(@Valid @RequestBody CreateUrlRequest request) {
        String encrypted = urlService.makeUrlShort(request.getLongUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(new UrlResponse(encrypted));
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
        String longUrl = urlService.restoreToLongUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", longUrl)
                .build();
    }

    @DeleteMapping("/url/{id}")
    public ResponseEntity<Void> deleteDbEntry(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 вместо 200
    }
}
