package org.mc.lexer;

/**
 * Created by dex on 24.03.2014.
 */

public class Token {

    public final TokenType type;
    public final int startOfsset;
    public final int endOffset;
    public final int line;
    public final int column;

    public Token(TokenType type, int startOfsset, int endOffset, int line, int column) {
        this.type = type;
        this.startOfsset = startOfsset;
        this.endOffset = endOffset;
        this.line = line;
        this.column = column;
    }

}
