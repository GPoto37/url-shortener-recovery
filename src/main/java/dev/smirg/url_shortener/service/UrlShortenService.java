package dev.smirg.url_shortener.service;

import dev.smirg.url_shortener.entity.UrlEntity;
import dev.smirg.url_shortener.exception.UrlNotFoundException;
import dev.smirg.url_shortener.repository.UrlRepository;
import dev.smirg.url_shortener.util.Base62Encoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenService {

    private final UrlRepository repository;
    private final Base62Encoder encoder;

    @Value("${app.secret}")
    private long secret;

    public UrlShortenService(UrlRepository repository, Base62Encoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public String makeUrlShort(String longUrl) {
        UrlEntity entity = repository.save(new UrlEntity(longUrl));
        long secretKey = entity.getId() ^ secret;
        return encoder.encode(secretKey);
    }

    public String restoreToLongUrl(String shortUrl) {
        long secretKey = encoder.decode(shortUrl);
        long id = secretKey ^ secret;
        return repository.findById(id)
                .orElseThrow(() -> new UrlNotFoundException("URL not found: " + shortUrl))
                .getLongUrl();
    }
}
