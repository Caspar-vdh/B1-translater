package com.dandykong.b1translater;

import com.dandykong.b1translater.model.tokenizer.Token;

import java.util.Arrays;
import java.util.List;

public class TestTools {
    public static List<Token> createTokenList(String... inputStrings) {
        return Arrays.stream(inputStrings).map(Token::fromString).toList();
    }
}
