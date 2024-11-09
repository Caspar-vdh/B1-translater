package com.dandykong.klinkendetaal.model.dictionary;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryTest {

    @Test
    public void testReadDictionary() throws URISyntaxException, IOException {
        Dictionary dictionary = new Dictionary();
        File dictionaryFile = new File(getClass().getResource("/Dictionary.txt").toURI());
        dictionary.read(dictionaryFile);

        assertEquals(new DictionaryEntry(new String[]{"informeren"}, new String[]{"laten", "weten"}),
                dictionary.getEntryForWord("informeren"));
        assertEquals(new DictionaryEntry(new String[]{"in", "strijd", "met"},
                new String[]{"niet", "toegestaan", "volgens"}),
                dictionary.getEntryForWord("in"));
        assertNull(dictionary.getEntryForWord("onbekend"));
    }
}