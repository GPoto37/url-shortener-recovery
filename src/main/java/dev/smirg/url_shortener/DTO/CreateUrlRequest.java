package dev.smirg.url_shortener.DTO;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class CreateUrlRequest {

    @NotBlank(message = "URL не должен быть пустым")
    @URL(message = "URL должен быть валидным")
    private String longUrl;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
