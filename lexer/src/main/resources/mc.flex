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
    return new DecNumberToken(yychar, yychar + yylength(), yyline, yycolumn, intValue);
}

private Token makeHexNumberToken(String value) {
    BigInteger intValue = new BigInteger(value.substring(2), 16);
    return new DecNumberToken(yychar, yychar + yylength(), yyline, yycolumn, intValue);
}

private Token makeDataToken(TokenType type) {
    return new DataToken(type, yychar, yychar + yylength(), yyline, yycolumn, yytext());
}

private void resetAfterState() {
    if (yystate() == AFTER) {
        yybegin(YYINITIAL);
    }
}

private final StringBuilder string = new StringBuilder();
private int begin = -1;
private int line = -1;
private int column= -1;

%}

LineEnding = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = [ \t\f]

SingleLineComment = "//" {InputCharacter}* {LineEnding}
MultiLIneComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
Comment = {SingleLineComment} | {MultiLIneComment}

DecInteger = 0 | [1-9][0-9]*
HexInteger = "0x" [0-9a-fA-F]+

Identifier = [a-zA-Z_$][a-zA-Z_$0-9]*

%state STRING
%state ERROR
%state AFTER

%%

<YYINITIAL> {
    // keywords
    "val" { yybegin(AFTER); return makeToken(TokenType.KwVal); }
    "var" { yybegin(AFTER); return makeToken(TokenType.KwVar); }

    // string
    \" { string.setLength(0);
         begin = yychar;
         line = yyline;
         column = yycolumn;
         yybegin(STRING); }

    // Identifier
    {Identifier} { yybegin(AFTER); return makeDataToken(TokenType.Id); }

    // Integer
    {DecInteger} { yybegin(AFTER); return makeNumberToken(yytext()); }
    {HexInteger} { yybegin(AFTER); return makeHexNumberToken(yytext()); }
}

<YYINITIAL, AFTER> {
    // operators
    "=" { resetAfterState(); return makeToken(TokenType.OpAssign); }
    "+" { resetAfterState(); return makeToken(TokenType.OpAdd); }
    "-" { resetAfterState(); return makeToken(TokenType.OpSub); }
    "*" { resetAfterState(); return makeToken(TokenType.OpMul); }
    "/" { resetAfterState(); return makeToken(TokenType.OpDiv); }

    // comments
    {Comment} { resetAfterState(); return makeToken(TokenType.Comment); }

    // whitespace
    {WhiteSpace} { resetAfterState(); }
    {LineEnding} { resetAfterState(); }

    [^] { string.setLength(0);
          begin = yychar;
          line = yyline;
          column = yycolumn;
          yybegin(ERROR); }
}


<STRING> {
    \" { yybegin(YYINITIAL);
         return new DataToken(TokenType.String, begin, yychar, line, column, string.toString()); }

    [^\n\r\"\\]+    { string.append(yytext()); }
    \\t             { string.append('\t'); }
    \\n             { string.append('\n'); }

    \\r             { string.append('\r'); }
    \\\"            { string.append('\"'); }
    \\              { string.append('\\'); }

    //TODO: {LineEnding}    { yybegin(ERROR); }
}

<ERROR> {
    {WhiteSpace}|{LineEnding} { yybegin(YYINITIAL);
                                return new DataToken(TokenType.Error, begin, yychar, line, column, string.toString()); }
    [^] { string.append(yytext()); }
}