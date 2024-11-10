package com.dandykong.klinkendetaal;

import com.dandykong.klinkendetaal.model.tokenizer.Tokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class KlinkendeTaalApplication {
	private static final Logger LOG = LoggerFactory.getLogger(KlinkendeTaalApplication.class);

	@Bean
	public Tokenizer tokenizer() {
		return new Tokenizer();
	}

	// run with "src/test/resources/Formeel.txt" as command line argument
	@Bean
	public CommandLineRunner run(Tokenizer tokenizer) {
		return args -> {
			LOG.info("Starting application");
			String input = Files.readString(Path.of(args[0]), Charset.forName("utf-8"));
			tokenizer.tokenize(input).forEach(System.out::println);
			LOG.info("Finishing application");
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(KlinkendeTaalApplication.class, args);
	}

}
