package org.mc.lexer;

import java.math.BigInteger;

%%

%class Lexer
%unicode
%line
%column
%char
%type Token
%function nextToken

%eofval{
    return new Token(TokenType.Eof, -1, -1, -1, -1);
%eofval}

%{

private Token makeToken(TokenType type) {
    return new Token(type, yychar, yychar + yylength(), yyline, yycolumn);
}

private Token makeNumberToken(String value) {
    BigInteger intValue = new BigInteger(value);
    return new NumberToken(TokenType.Number, yychar, yychar + yylength(), yyline, yycolumn, intValue);
}

private Token makeHexNumberToken(String value) {
    BigInteger intValue = new BigInteger(value.substring(2), 16);
    return new NumberToken(TokenType.Number, yychar, yychar + yylength(), yyline, yycolumn, intValue);
}

private Token makeStringToken(TokenType type, String value) {
    return new StringToken(type, yychar, yychar + yylength(), yyline, yycolumn, value);
}

private final StringBuilder string = new StringBuilder();
private int stringBegin = -1;
private int stringLine = -1;
private int stringColumn= -1;

%}

LineEnding = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = [ \t\f]

SingleLineComment = "//" {InputCharacter}* {LineEnding}
MultiLIneComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
Comment = {SingleLineComment} | {MultiLIneComment}

DecInteger = 0 | [1-9][0-9]*
HexInteger = "0x" [0-9a-fA-F]+

Identifier = [a-zA-Z_][a-zA-Z_0-9]*

%state STRING

%%

<YYINITIAL> {
    // keywords
    "val" { return makeToken(TokenType.KwVal); }
    "var" { return makeToken(TokenType.KwVar); }

    // string
    \" { string.setLength(0);
         stringBegin = yychar;
         stringLine = yyline;
         stringColumn = yycolumn;
         yybegin(STRING); }

    // operators
    "=" { return makeToken(TokenType.OpAssign); }

    // Identifier
    {Identifier} { return makeStringToken(TokenType.Id, yytext()); }

    // Integer
    {DecInteger} { return makeNumberToken(yytext()); }
    {HexInteger} { return makeHexNumberToken(yytext()); }

    // comments
    {Comment} { return makeToken(TokenType.Comment); }
    // whitespace
    {WhiteSpace} { /* ignore */ }
    {LineEnding} { /* ignore */ }
}

<STRING> {
    \" { yybegin(YYINITIAL);
         return new StringToken(TokenType.String, stringBegin, yychar, stringLine, stringColumn, string.toString()); }

    [^\n\r\"\\]+                   { string.append(yytext()); }
    \\t                            { string.append('\t'); }
    \\n                            { string.append('\n'); }

    \\r                            { string.append('\r'); }
    \\\"                           { string.append('\"'); }
    \\                             { string.append('\\'); }
}

[^] {}