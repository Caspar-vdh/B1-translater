package com.dandykong.klinkendetaal.model.tokenizer;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    @Test
    public void testTokenize() throws URISyntaxException, IOException {
        String input = Files.readString(Path.of(Objects.requireNonNull(getClass().getResource("/Formeel.txt")).toURI()),
                StandardCharsets.UTF_8);

        // The text should result in 74 + 8 + 9 = 91 tokens:
        // - 73 word tokens
        // - 8 punctuation tokens
        // - 9 newline tokens
        List<Token> tokenList = new Tokenizer().tokenize(input);
        assertEquals(90, tokenList.size());

        List<Token> wordList = tokenList
                .stream()
                .filter(token -> token.getType() == Token.Type.WORD)
                .toList();
        assertEquals(73, wordList.size());

        List<Token> punctuationList = tokenList
                .stream()
                .filter(token -> token.getType() == Token.Type.PUNCTUATION)
                .toList();
        assertEquals(8, punctuationList.size());

        List<Token> newLineList = tokenList
                .stream()
                .filter(token -> token.getType() == Token.Type.NEWLINE)
                .toList();
        assertEquals(9, newLineList.size());
    }
}