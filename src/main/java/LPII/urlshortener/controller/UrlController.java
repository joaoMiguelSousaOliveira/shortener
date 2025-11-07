package LPII.urlshortener.controller;

import LPII.urlshortener.controller.dto.ShortUrlRequest;
import LPII.urlshortener.controller.dto.ShortUrlResponse;
import LPII.urlshortener.entities.UrlEntity;
import LPII.urlshortener.repository.UrlRepository;
import LPII.urlshortener.service.SequenceGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.hashids.Hashids;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

// TODO: Configure a more restrictive CORS policy in a production environment
@RestController
public class UrlController {

    private final UrlRepository urlRepository;
    private final Hashids hashids;
    private final SequenceGenerator sequenceGenerator;

    public UrlController(UrlRepository urlRepository, Hashids hashids, SequenceGenerator sequenceGenerator) {
        this.urlRepository = urlRepository;
        this.hashids = hashids;
        this.sequenceGenerator = sequenceGenerator;
    }

    @PostMapping(value = "/shorten-url")
    public ResponseEntity<ShortUrlResponse> shortenUrl(@RequestBody ShortUrlRequest shortUrlRequest, HttpServletRequest servletRequestrequest) {
        var urlEntity = new UrlEntity();
        urlEntity.setFullurl(shortUrlRequest.url());
        urlEntity.setExpiredAt(LocalDateTime.now().plusHours(1));

        urlEntity.setId(sequenceGenerator.generateSequence(UrlEntity.SEQUENCE_NAME));

        var hash = hashids.encode(urlEntity.getId());
        urlEntity.setHash(hash);

        urlRepository.save(urlEntity);

        var redirectUrl = ServletUriComponentsBuilder
                .fromRequestUri(servletRequestrequest)
                .replacePath("/{hash}")
                .buildAndExpand(hash)
                .toUriString();

        return ResponseEntity.ok(new ShortUrlResponse(redirectUrl));

    }



    @GetMapping("/{hash}")
    public ResponseEntity<Void> redirect(@PathVariable String hash) {

        var decodedHash = hashids.decode(hash);
        if (decodedHash.length == 0) {
            return ResponseEntity.notFound().build();
        }

        var id = decodedHash[0];

        var url = urlRepository.findById(id);

        if (url.isEmpty() || url.get().getExpiredAt().isBefore(LocalDateTime.now())) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(URI.create(url.get().getFullUrl()));

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();

    }

}
