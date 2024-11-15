package com.dandykong.b1translater.model.translater;

import com.dandykong.b1translater.model.dictionary.Dictionary;
import com.dandykong.b1translater.model.tokenizer.Token;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import static com.dandykong.b1translater.TestTools.createTokenList;
import static org.junit.jupiter.api.Assertions.*;

class TranslaterTest {

    private static final Dictionary dictionary = new Dictionary();

    @BeforeAll
    public static void setUp() throws IOException, URISyntaxException {
        File dictionaryFile = new File(Objects.requireNonNull(TranslaterTest.class.getResource("/Dictionary.json")).toURI());
        dictionary.read(dictionaryFile);
    }

    @Test
    public void translateMultipleTokenPhrase() {
        // the dictionary translates tokens "na", "deze", "termijn" to "dan", "nog"
        Translater translater = new Translater(dictionary);
        List<Token> originalTokenList = createTokenList("na", "deze", "termijn");
        List<Token> translatedTokenList = translater.translate(originalTokenList);
        List<Token> expectedTokenList = createTokenList("dan", "nog");
        assertEquals(expectedTokenList, translatedTokenList);

        // if the first two tokens are the same, but the third one is not, it should use the original tokens
        originalTokenList = createTokenList("na", "deze", "periode");
        translatedTokenList = translater.translate(originalTokenList);
        assertEquals(originalTokenList, translatedTokenList);

        // the phrase to translate is "na dagtekening"
        // the word "na" matches "na deze termijn", but that should not be used
        // the word "dagtekening" has its own entry in the dictionary
        originalTokenList = createTokenList("na", "dagtekening");
        translatedTokenList = translater.translate(originalTokenList);
        expectedTokenList = createTokenList("na", "de", "datum");
        assertEquals(expectedTokenList, translatedTokenList);
    }

    @Test
    public void retainCapitalizedFirstToken() {
        Translater translater = new Translater(dictionary);
        // Original sentence starts with a capital letter
        List<Token> originalTokenList = createTokenList("Na", "deze", "termijn");
        List<Token> translatedTokenList = translater.translate(originalTokenList);
        // result should start with a capital letter too
        List<Token> expectedTokenList = createTokenList("Dan", "nog");
        assertEquals(expectedTokenList, translatedTokenList);
        assertTrue(translatedTokenList.get(0).capitalized());
    }

    @Test
    public void retainPunctuationAndNewLines() {
        Translater translater = new Translater(dictionary);
        List<Token> originalTokenList = createTokenList("verwijderd", "?");
        List<Token> translatedTokenList = translater.translate(originalTokenList);
        List<Token> expectedTokenList = createTokenList("weggehaald", "?");
        assertEquals(expectedTokenList, translatedTokenList);

        originalTokenList = createTokenList("gestald", System.lineSeparator());
        translatedTokenList = translater.translate(originalTokenList);
        expectedTokenList = createTokenList("neergezet", System.lineSeparator());
        assertEquals(expectedTokenList, translatedTokenList);
    }


}