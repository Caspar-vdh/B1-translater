package com.dandykong.b1translater.model.tokenizer;

import java.util.regex.Pattern;

public record Token(Type type, String value, boolean capitalized) {

    public enum Type {
        WORD, PUNCTUATION, NEWLINE;
    }

    public Token(Type type, String value) {
        this(type, value, false);
    }

    public static Token fromString(String input) {
        char firstChar = input.charAt(0);
        if (Character.isLetter(firstChar)) {
            return new Token(Type.WORD, input.toLowerCase(), Character.isUpperCase(firstChar));
        } else if (Pattern.matches("\\p{Punct}", input)){
            return new Token(Type.PUNCTUATION, input);
        } else if (input.equals(System.getProperty("line.separator", "\n"))) {
            return new Token(Type.NEWLINE, input);
        }
        throw new IllegalArgumentException("Not recognized by tokenizer: " + input);
    }
}
