package com.dandykong.klinkendetaal.model.tokenizer;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Tokenizer {
    public List<Token> tokenize(String text) {
        return Pattern.compile("\\p{IsLatin}+|,|\\.|\n|(\r\n)")
                .matcher(text)
                .results()
                .map(MatchResult::group)
                .map(Token::fromString)
                .collect(Collectors.toList());
    }
}
