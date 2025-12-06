package LPII.urlshortener.controller;

import LPII.urlshortener.controller.dto.ShortUrlRequest;
import LPII.urlshortener.controller.dto.ShortUrlResponse;
import LPII.urlshortener.controller.entities.Url;
import LPII.urlshortener.controller.repositories.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.hashids.Hashids;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*") 
public class UrlController {

    private static final Logger logger = Logger.getLogger(UrlController.class.getName());

    private final Hashids hashids;
    private final UrlRepository urlRepository;

    public UrlController(Hashids hashids, UrlRepository urlRepository) {
        this.hashids = hashids;
        this.urlRepository = urlRepository;
    }

    @PostMapping(value = "/shorten-url")
    public ResponseEntity<ShortUrlResponse> shortenUrl(@RequestBody ShortUrlRequest shortUrlRequest, HttpServletRequest servletRequest) {
        try {
            String originalUrl = shortUrlRequest.url();

            // Validação de entrada
            if (originalUrl == null || originalUrl.trim().isEmpty()) {
                logger.warning("Attempt to shorten empty URL");
                return ResponseEntity.badRequest().build();
            }

            String trimmedUrl = originalUrl.trim();

            // Validar formato da URL
            if (!isValidUrl(trimmedUrl)) {
                logger.warning("Invalid URL format: " + trimmedUrl);
                return ResponseEntity.badRequest().build();
            }

            // Gerar hash único
            String hash = hashids.encode(System.currentTimeMillis());
            LocalDateTime expirationTime = LocalDateTime.now().plusHours(1);

            // Salvar no MongoDB
            Url urlEntity = new Url(hash, trimmedUrl, expirationTime);
            urlRepository.save(urlEntity);

            var redirectUrl = ServletUriComponentsBuilder
                    .fromRequestUri(servletRequest)
                    .replacePath("/{hash}")
                    .buildAndExpand(hash)
                    .toUriString();

            logger.info("URL shortened successfully: " + hash + " -> " + trimmedUrl);
            return ResponseEntity.ok(new ShortUrlResponse(redirectUrl));

        } catch (Exception e) {
            logger.severe("Error shortening URL: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    private boolean isValidUrl(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }

        try {
            URI uri = new URI(url);
            if (uri.getScheme() == null) {
                return false;
            }
            String scheme = uri.getScheme().toLowerCase();
            return "http".equals(scheme) || "https".equals(scheme);
        } catch (URISyntaxException e) {
            return false;
        }
    }

    @GetMapping("/{hash}")
    public ResponseEntity<Void> redirect(@PathVariable String hash) {
        try {
            if (hash == null || hash.trim().isEmpty()) {
                logger.warning("Empty hash requested");
                return ResponseEntity.notFound().build();
            }

            String trimmedHash = hash.trim();

            // Buscar no MongoDB
            var urlOptional = urlRepository.findById(trimmedHash);

            if (urlOptional.isEmpty()) {
                logger.warning("Hash not found: " + trimmedHash);
                return ResponseEntity.notFound().build();
            }

            Url urlEntity = urlOptional.get();

            // Verificar se expirou
            if (urlEntity.getExpiredAt().isBefore(LocalDateTime.now())) {
                logger.info("Expired URL accessed: " + trimmedHash);
                urlRepository.delete(urlEntity);
                return ResponseEntity.notFound().build();
            }

            String originalUrl = urlEntity.getFullUrl();

            if (!isValidUrl(originalUrl)) {
                logger.severe("Invalid stored URL for hash: " + trimmedHash);
                return ResponseEntity.notFound().build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(originalUrl));

            logger.info("Redirecting: " + trimmedHash + " -> " + originalUrl);
            return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();

        } catch (Exception e) {
            logger.severe("Error redirecting hash " + hash + ": " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}