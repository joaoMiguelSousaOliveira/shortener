package LPII.urlshortener.controller.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Document(collection = "urls")
public class Url {
    @Id
    private String id;

    private String fullUrl;

    @Indexed(expireAfterSeconds = 3600)
    private LocalDateTime expiredAt;

    // Construtores
    public Url() {}

    public Url(String id, String fullUrl, LocalDateTime expiredAt) {
        this.id = id;
        this.fullUrl = fullUrl;
        this.expiredAt = expiredAt;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }
}