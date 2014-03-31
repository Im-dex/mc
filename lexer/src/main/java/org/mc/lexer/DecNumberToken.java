package org.mc.lexer;

import java.math.BigInteger;

/**
 * Created by dex on 24.03.2014.
 */
public class DecNumberToken extends Token {

    public final BigInteger value;

    public DecNumberToken(int startOffset, int endOffset, int line, int column, BigInteger value) {
        super(TokenType.Number, startOffset, endOffset, line, column);
        this.value = value;
    }
}
