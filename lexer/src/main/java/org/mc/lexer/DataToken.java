package org.mc.lexer;

/**
 * Created by dex on 24.03.2014.
 */
public class DataToken extends Token {

    public final String value;

    public DataToken(TokenType type, int startOffset, int endOffset, int line, int column, String value) {
        super(type, startOffset, endOffset, line, column);
        this.value = value;
    }
}
