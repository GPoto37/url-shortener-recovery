package dev.smirg.url_shortener.repository;

import dev.smirg.url_shortener.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
}
