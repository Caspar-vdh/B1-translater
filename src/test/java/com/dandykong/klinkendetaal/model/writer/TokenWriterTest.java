package com.dandykong.klinkendetaal.model.writer;

import com.dandykong.klinkendetaal.model.tokenizer.Token;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import static com.dandykong.klinkendetaal.TestTools.createTokenList;
import static org.junit.jupiter.api.Assertions.*;

class TokenWriterTest {

    @Test
    public void testWrite() throws IOException {
        List<Token> tokenList = createTokenList("Hallo", ",", "wereld", ".", System.lineSeparator(), "Dit", "is",
                "klinkende", "taal", "!");
        StringWriter writer = new StringWriter();
        TokenWriter tokenWriter = new TokenWriter();
        tokenWriter.write(tokenList, writer);
        assertEquals("Hallo, wereld." + System.lineSeparator() + "Dit is klinkende taal!", writer.toString());
    }
}