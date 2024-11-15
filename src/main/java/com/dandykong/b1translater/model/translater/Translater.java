package com.dandykong.b1translater.model.translater;

import com.dandykong.b1translater.model.dictionary.Dictionary;
import com.dandykong.b1translater.model.dictionary.DictionaryEntry;
import com.dandykong.b1translater.model.tokenizer.Token;

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
            if (token.type() == Token.Type.WORD) {
                DictionaryEntry dictionaryEntry = dictionary.getEntryForWord(token.value());
                if (dictionaryEntry == null) {
                    translatedTokens.add(token);
                } else {
                    boolean shouldRollBack = false;
                    int rollBack = 0;
                    boolean isFirstTokenCapitalized = token.capitalized();
                    for (int i = 1; i < dictionaryEntry.getKey().length; i++) {
                        if (!iterator.hasNext()) {
                            shouldRollBack = true;
                            break;
                        }
                        Token nextToken = iterator.next();
                        rollBack++;
                        if (!nextToken.value().equals(dictionaryEntry.getKey()[i])) {
                            shouldRollBack = true;
                        }
                    }
                    if (shouldRollBack) {
                        // roll back the iterator and then add the original tokens
                        translatedTokens.add(token);
                        for (int i = 0; i < rollBack; i++) {
                            iterator.previous();
                        }
                    } else {
                        // it was a match, create tokens for the translation
                        String[] translatedStrings = dictionaryEntry.getValue();
                        IntStream.range(0, translatedStrings.length)
                                .mapToObj(i -> new Token(Token.Type.WORD, translatedStrings[i], i == 0 && isFirstTokenCapitalized))
                                .forEach(translatedTokens::add);
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
