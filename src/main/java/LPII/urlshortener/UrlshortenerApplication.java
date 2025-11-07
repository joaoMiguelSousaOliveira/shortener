package LPII.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.hashids.Hashids;

@SpringBootApplication
public class UrlshortenerApplication {

	@Value("${hashids.salt}")
	private String hashidsSalt;

	@Bean
	public Hashids hashids() {
		return new Hashids(hashidsSalt);
	}

	public static void main(String[] args) {
		SpringApplication.run(UrlshortenerApplication.class, args);
	}

}
