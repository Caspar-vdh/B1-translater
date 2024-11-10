package com.dandykong.klinkendetaal.model;

import com.dandykong.klinkendetaal.model.dictionary.Dictionary;
import com.dandykong.klinkendetaal.model.dictionary.DictionaryEntry;
import com.dandykong.klinkendetaal.model.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.IntStream;

public class Translater {
    private final Dictionary dictionary;

    public Translater(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public List<Token> translate(List<Token> originalTokens) {
        List<Token> translatedTokens = new ArrayList<>();

        ListIterator<Token> iterator = originalTokens.listIterator();
        while (iterator.hasNext()) {
            Token token = iterator.next();
            if (token.getType() == Token.Type.WORD) {
                DictionaryEntry dictionaryEntry = dictionary.getEntryForWord(token.getValue());
                if (dictionaryEntry == null) {
                    translatedTokens.add(token);
                } else {
                    boolean validPhraseFound = true;
                    boolean isFirstTokenCapitalized = token.isCapitalized();
                    List<Token> originalPhrase = new ArrayList<>(List.of(token));
                    for (int i = 1; i < dictionaryEntry.getKey().length; i++) {
                        if (!iterator.hasNext()) {
                            validPhraseFound = false;
                            break;
                        }
                        Token nextToken = iterator.next();
                        originalPhrase.add(nextToken);
                        if (!nextToken.getValue().equals(dictionaryEntry.getKey()[i])) {
                            validPhraseFound = false;
                        }
                    }
                    if (validPhraseFound) {
                        // it was a match, create tokens for the translation
                        String[] translatedStrings = dictionaryEntry.getValue();
                        IntStream.range(0, translatedStrings.length)
                                .mapToObj(i -> new Token(Token.Type.WORD, translatedStrings[i], i == 0 && isFirstTokenCapitalized))
                                .forEach(translatedTokens::add);
                    } else {
                        // roll back the iterator and then add the original tokens
                        translatedTokens.addAll(originalPhrase);
                    }
                }
            } else {
                // punctuation or newlines don't need translating
                translatedTokens.add(token);
            }
        }
        return translatedTokens;
    }
}
