package com.dandykong.klinkendetaal.model.tokenizer;

import java.util.Objects;
import java.util.regex.Pattern;

public class Token {


    public enum Type {
        WORD, PUNCTUATION, NEWLINE;
    }

    private final Type type;
    private final String value;
    private final boolean capitalized;

    public Token(Type type, String value) {
        this(type, value, false);
    }

    public Token(Type type, String value, boolean capitalized) {
        this.type = type;
        this.value = value;
        this.capitalized = capitalized;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public boolean isCapitalized() {
        return capitalized;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return capitalized == token.capitalized && type == token.type && Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value, capitalized);
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                ", capitalized=" + capitalized +
                '}';
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
