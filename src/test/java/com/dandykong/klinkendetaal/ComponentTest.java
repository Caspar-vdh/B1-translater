package com.dandykong.klinkendetaal;

import com.dandykong.klinkendetaal.model.dictionary.Dictionary;
import com.dandykong.klinkendetaal.model.tokenizer.Token;
import com.dandykong.klinkendetaal.model.tokenizer.Tokenizer;
import com.dandykong.klinkendetaal.model.translater.Translater;
import com.dandykong.klinkendetaal.model.writer.TokenWriter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComponentTest {

    @Test
    public void testFlow() throws URISyntaxException, IOException {
        Tokenizer tokenizer = new Tokenizer();
        Dictionary dictionary = new Dictionary();
        Translater translater = new Translater(dictionary);
        TokenWriter tokenWriter = new TokenWriter();

        String input = Files.readString(Path.of(Objects.requireNonNull(getClass().getResource("/Formeel.txt")).toURI()),
                StandardCharsets.UTF_8);
        List<Token> inputTokens = tokenizer.tokenize(input);

        File dictionaryFile = new File(Objects.requireNonNull(getClass().getResource("/Dictionary.json")).toURI());
        dictionary.read(dictionaryFile);

        List<Token> translatedTokens = translater.translate(inputTokens);

        StringWriter writer = new StringWriter();
        tokenWriter.write(translatedTokens, writer);

        String expectedOutput = Files.readString(Path.of(Objects.requireNonNull(getClass().getResource("/B1.txt"))
                .toURI()), StandardCharsets.UTF_8);

        assertEquals(expectedOutput, writer.toString());
    }

}
