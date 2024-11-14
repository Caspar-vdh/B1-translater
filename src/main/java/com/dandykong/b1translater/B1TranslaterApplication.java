package com.dandykong.b1translater;

import com.dandykong.b1translater.model.dictionary.Dictionary;
import com.dandykong.b1translater.model.tokenizer.Token;
import com.dandykong.b1translater.model.tokenizer.Tokenizer;
import com.dandykong.b1translater.model.translater.Translater;
import com.dandykong.b1translater.model.writer.TokenWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
public class B1TranslaterApplication {
	private static final Logger LOG = LoggerFactory.getLogger(B1TranslaterApplication.class);

	@Bean
	public Tokenizer tokenizer() {
		return new Tokenizer();
	}

	@Bean
	public Dictionary dictionary(ApplicationArguments arguments) {
		try {
			Dictionary dictionary = new Dictionary();
			String dictionaryFilePath = arguments.getSourceArgs()[0];
			File dictionaryFile = new File(dictionaryFilePath);
			dictionary.read(dictionaryFile);
			return dictionary;
		} catch (IOException e) {
			throw new BeanCreationException("Failed to create Dictionary bean, invalid file");
		}
	}

	@Bean
	public Translater translater(Dictionary dictionary) {
		return new Translater(dictionary);
	}

	@Bean
	public TokenWriter tokenWriter() {
		return new TokenWriter();
	}

	// run with
	// 	- "src/test/resources/Dictionary.json"
	// 	- "src/test/resources/Formeel.txt"
	// 	as command line arguments
	@Bean
	public CommandLineRunner run(Tokenizer tokenizer, Translater translater, TokenWriter tokenWriter) {
		return args -> {
			LOG.info("Starting application");
			String input = Files.readString(Path.of(args[1]), StandardCharsets.UTF_8);
			List<Token> inputTokens = tokenizer.tokenize(input);
			List<Token> translatedTokens = translater.translate(inputTokens);
			StringWriter writer = new StringWriter();
			tokenWriter.write(translatedTokens, writer);
            LOG.info("\n************************************************************\n\n" +
					"{}\n************************************************************\n", writer.toString());
			LOG.info("Finishing application");
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(B1TranslaterApplication.class, args);
	}

}
