package com.dandykong.klinkendetaal;

import com.dandykong.klinkendetaal.model.dictionary.Dictionary;
import com.dandykong.klinkendetaal.model.tokenizer.Tokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@SpringBootApplication
public class KlinkendeTaalApplication {
	private static final Logger LOG = LoggerFactory.getLogger(KlinkendeTaalApplication.class);

	@Bean
	public Tokenizer tokenizer() {
		return new Tokenizer();
	}

	@Bean
	public Dictionary dictionary(ApplicationArguments arguments) {
		Dictionary dictionary = new Dictionary();
		String dictionaryFilePath = arguments.getSourceArgs()[0];
		File dictionaryFile = new File(dictionaryFilePath);
		dictionary.read(dictionaryFile);
		return dictionary;
	}

	// run with
	// 	- "src/test/resources/Dictionary.json"
	// 	- "src/test/resources/Formeel.txt"
	// 	as command line arguments
	@Bean
	public CommandLineRunner run(Tokenizer tokenizer) {
		return args -> {
			LOG.info("Starting application");
			String input = Files.readString(Path.of(args[1]), StandardCharsets.UTF_8);
			tokenizer.tokenize(input).forEach(System.out::println);
			LOG.info("Finishing application");
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(KlinkendeTaalApplication.class, args);
	}

}
