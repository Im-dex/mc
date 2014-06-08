package org.mc.lexer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

%%

%class McLexer
%unicode
%line
%column
%char
%public
%type Token
%function nextToken

%eofval{
    return makeToken(Token.Eof.class);
%eofval}

%{

private int getTokenEndOffset() {
    return yychar + yytext().length();
}

private TokenPosition calcTokenPosition(int line, int column, int index) {
    return new TokenPosition(line, column, yychar, getTokenEndOffset(), index);
}

private <T extends Token> T makeIndexlessToken(Class<T> type) {
    final TokenPosition position = calcTokenPosition(yyline, yycolumn, 0);
    return makeToken(type, position);
}

private <T extends Token> T makeToken(Class<T> type) {
    final TokenPosition position = calcTokenPosition(yyline, yycolumn, tokenIndex++);
    return makeToken(type, position);
}

private <T extends Token> T makeToken(Class<T> type, TokenPosition position) {
    try {
        final Constructor<T> constructor = type.getDeclaredConstructor(TokenPosition.class);
        return constructor.newInstance(position);
    } catch (NoSuchMethodException e) {
        throw new IllegalStateException(e);
    } catch (InvocationTargetException e) {
        throw new IllegalStateException(e);
    } catch (InstantiationException e) {
        throw new IllegalStateException(e);
    } catch (IllegalAccessException e) {
        throw new IllegalStateException(e);
    }
}

private int tokenIndex = 0;

%}

WhiteSpace = [\ \n\r\t\f]
SingleLineComment = "//"[^\r\n]*
MultiLIneComment = ("/*"([^"*"]*("*"+[^"*""/"])?)*("*"+"/")?)|"/*"

Digit = [0-9]
DigitOrUnderscore = [0-9_]
Digits = {Digit}|{Digit}{DigitOrUnderscore}*

HexDigit = [0-9A-Fa-f]
HexDigitOrUnderscore = [0-9A-Fa-f_]
HexDigits = {HexDigit}|{HexDigit}{HexDigitOrUnderscore}*

BinDigit = [0-1]
BinDigitOrUnderscore = [0-1_]
BinDigits = {BinDigit}|{BinDigitOrUnderscore}*

DecNumberLiteral = {Digits}
HexNumberLiteral = 0[Xx]{HexDigits}
BinNumberLiteral = 0[Bb]{BinDigits}

FloatLiteral =  {DecFpNumber} [Ff]
DoubleLiteral = {DecFpNumber} [Dd]?
DecFpNumber = {DecSignificand}{DecExponent}?
DecSignificand = {Digits} | "." {Digits} | {Digits} "." {Digits}
DecExponent = [Ee][+-]{Digits}

HexFloatLiteral = {HexFpNumber} [Ff]
HexDoubleLiteral = {HexFpNumber} [Dd]?
HexFpNumber = 0[Xx]{HexSignificand}{HexExponent}?
HexSignificand = {HexDigits} | "." {HexDigits} | {HexDigits} "." {HexDigits}
HexExponent = [Pp][+-]{HexDigits}

CharacterLiteral = "'"{Symbol}*"'"
StringLiteral = \"{Symbol}*\"
Symbol = [^\\\'\r\n]|{EscapeSequence}
EscapeSequence = \\[^\r\n]

Identifier = [a-zA-Z][a-zA-Z_$0-9]*

%%

<YYINITIAL> {
    {WhiteSpace}+       { return makeIndexlessToken(Token.Whitespace.class); }

    {MultiLIneComment}  { return makeIndexlessToken(Token.MultiLineComment.class); }
    {SingleLineComment} { return makeIndexlessToken(Token.SingleLineComment.class); }

    {DecNumberLiteral}  { return makeToken(Token.DecNumber.class); }
    {HexNumberLiteral}  { return makeToken(Token.HexNumber.class); }
    {BinNumberLiteral}  { return makeToken(Token.BinNumber.class); }
    {FloatLiteral}      { return makeToken(Token.Float.class); }
    {HexFloatLiteral}   { return makeToken(Token.HexFloat.class); }
    {DoubleLiteral}     { return makeToken(Token.Double.class); }
    {HexDoubleLiteral}  { return makeToken(Token.HexDouble.class); }
    {CharacterLiteral}  { return makeToken(Token.Char.class); }
    {StringLiteral}     { return makeToken(Token.String.class); }

    // keywords
    "val"               { return makeToken(Token.KwVal.class); }
    "var"               { return makeToken(Token.KwVar.class); }
    "def"               { return makeToken(Token.KwDef.class); }
    "class"             { return makeToken(Token.KwClass.class); }
    "interface"         { return makeToken(Token.KwInterface.class); }
    "public"            { return makeToken(Token.KwPublic.class); }
    "private"           { return makeToken(Token.KwPrivate.class); }
    "final"             { return makeToken(Token.KwFinal.class); }
    "extends"           { return makeToken(Token.KwExtends.class); }
    "implements"        { return makeToken(Token.KwImplements.class); }
    "override"          { return makeToken(Token.KwOverride.class); }
    "as"                { return makeToken(Token.KwAs.class); }
    "is"                { return makeToken(Token.KwIs.class); }
    "this"              { return makeToken(Token.KwThis.class); }
    "super"             { return makeToken(Token.KwSuper.class); }

    {Identifier}        { return makeToken(Token.Id.class); }

    // delimiters
    ";"                 { return makeToken(Token.Semicolon.class); }
    ","                 { return makeToken(Token.Comma.class); }
    "("                 { return makeToken(Token.OpenParen.class); }
    ")"                 { return makeToken(Token.CloseParen.class); }
    "{"                 { return makeToken(Token.OpenBrace.class); }
    "}"                 { return makeToken(Token.CloseBrace.class); }

    // operators
    "="                 { return makeToken(Token.Assign.class); }
    "+"                 { return makeToken(Token.Plus.class);}
    "-"                 { return makeToken(Token.Minus.class); }
    "*"                 { return makeToken(Token.Asterisk.class); }
    "/"                 { return makeToken(Token.Div.class); }

    .                   { return makeToken(Token.BadCharacter.class); }
}