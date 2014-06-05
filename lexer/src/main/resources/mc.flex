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
    return makeToken(Eof.class);
%eofval}

%{

private int getTokenEndOffset() {
    return yychar - yytext().length();
}

private TokenPosition calcTokenPosition(int line, int column) {
    return new TokenPosition(line, column, yychar, getTokenEndOffset(), tokenIndex++);
}

private <T extends Token> T makeToken(Class<T> type) {
    try {
        final TokenPosition position = calcTokenPosition(yyline, yycolumn);
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
BinDigits = {BinDigit}|{BinDigitOrUnderscore}

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
    {WhiteSpace}+       { return makeToken(Whitespace.class); }

    {MultiLIneComment}  { return makeToken(MultiLineComment.class); }
    {SingleLineComment} { return makeToken(SingleLineComment.class); }

    {DecNumberLiteral}  { return makeToken(DecNumberLiteral.class); }
    {HexNumberLiteral}  { return makeToken(HexNumberLiteral.class); }
    {BinNumberLiteral}  { return makeToken(BinNumberLiteral.class); }
    {FloatLiteral}      { return makeToken(FloatLiteral.class); }
    {HexFloatLiteral}   { return makeToken(HexFloatLiteral.class); }
    {DoubleLiteral}     { return makeToken(DoubleLiteral.class); }
    {HexDoubleLiteral}  { return makeToken(HexDoubleLiteral.class); }
    {CharacterLiteral}  { return makeToken(CharLiteral.class); }
    {StringLiteral}     { return makeToken(StringLiteral.class); }

    // keywords
    "val"               { return makeToken(KwVal.class); }
    "var"               { return makeToken(KwVar.class); }
    "def"               { return makeToken(KwDef.class); }
    "class"             { return makeToken(KwClass.class); }
    "interface"         { return makeToken(KwInterface.class); }
    "public"            { return makeToken(KwPublic.class); }
    "private"           { return makeToken(KwPrivate.class); }
    "final"             { return makeToken(KwFinal.class); }
    "extends"           { return makeToken(KwExtends.class); }
    "implements"        { return makeToken(KwImplements.class); }
    "override"          { return makeToken(KwOverride.class); }
    "as"                { return makeToken(KwAs.class); }
    "is"                { return makeToken(KwIs.class); }
    "this"              { return makeToken(KwThis.class); }
    "super"             { return makeToken(KwSuper.class); }

    {Identifier}        { return makeToken(IdLiteral.class); }

    // delimiters
    ";"                 { return makeToken(Semicolon.class); }
    ","                 { return makeToken(Comma.class); }
    "("                 { return makeToken(OpenParen.class); }
    ")"                 { return makeToken(CloseParen.class); }
    "{"                 { return makeToken(OpenBrace.class); }
    "}"                 { return makeToken(CloseBrace.class); }

    // operators
    "="                 { return makeToken(Assign.class); }
    "+"                 { return makeToken(Plus.class);}
    "-"                 { return makeToken(Minus.class); }
    "*"                 { return makeToken(Asterisk.class); }
    "/"                 { return makeToken(Div.class); }

    .                   { return makeToken(BadCharacter.class); }
}