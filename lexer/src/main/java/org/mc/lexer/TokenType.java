package org.mc.lexer;

/**
 * Created by dex on 24.03.2014.
 */
public enum TokenType {
    Id,
    Number,
    String,
    Eof,
    Comment,
    Error,

    KwVal,
    KwVar,

    Semicolon,

    Assign,
    Plus,
    Minus,
    Times,
    Divide,

    LeftParen,
    RightParen
}
