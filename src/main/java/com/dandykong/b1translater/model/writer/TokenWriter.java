package com.dandykong.b1translater.model.writer;

import com.dandykong.b1translater.model.tokenizer.Token;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class TokenWriter {
    public void write(List<Token> tokens, Writer writer) throws IOException {
        // Word tokens should be prepended with a space, except the first one and directly after a newline
        boolean addSpace = false;

        for (Token token : tokens) {
            switch (token.type()) {
                case WORD -> {
                    if (addSpace) {
                        writer.write(" ");
                    }
                    String value = token.capitalized() ?
                            StringUtils.capitalize(token.value()) :
                            token.value();
                    writer.write(value);
                    addSpace = true;
                }
                case PUNCTUATION -> {
                    writer.write(token.value());
                }
                case NEWLINE -> {
                    writer.write(token.value());
                    addSpace = false;
                }
            }
        }
    }
}

