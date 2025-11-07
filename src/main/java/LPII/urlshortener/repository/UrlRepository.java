package LPII.urlshortener.repository;

import LPII.urlshortener.entities.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<UrlEntity, Long> {
}
