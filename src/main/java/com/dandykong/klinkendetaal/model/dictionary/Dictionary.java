package com.dandykong.klinkendetaal.model.dictionary;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Dictionary {

    // Store entries in a map where the keys are the first word
    Map<String, DictionaryEntry> entryMap = new HashMap<>();

    void read(File input) throws IOException {
        DictionaryEntry[] dictionaryEntries = new ObjectMapper().readValue(input, DictionaryEntry[].class);
        for (DictionaryEntry entry : dictionaryEntries) {
            entryMap.put(entry.getKey()[0], entry);
        }
    }

    DictionaryEntry getEntryForWord(String word) {
        return entryMap.get(word);
    }
}
