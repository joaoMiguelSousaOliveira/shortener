package LPII.urlshortener.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

// Passa o nome da tabela
@Document(collection = "urls")

public class UrlEntity {
    public static final String SEQUENCE_NAME = "urls_sequence";

    @Id
    private long id;
    String hash;
    private String fullurl;

    @Indexed(expireAfter = "1h")
    private LocalDateTime expiredAt;

    public UrlEntity(){}

    public UrlEntity(long id, String fullurl, String hash, LocalDateTime expiredAt) {
        this.id = id;
        this.fullurl = fullurl;
        this.hash = hash;
        this.expiredAt = expiredAt;
    }

    public long getId() {
        return id;
    }

    public String getFullUrl() { return fullurl; }

    public String getHash() { return hash; }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setFullurl(String fullurl) {
        this.fullurl = fullurl;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setId(long id) {
        this.id = id;
    }

}
