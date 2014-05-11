package org.mc.lexer;

import java.lang.reflect.Constructor;
import scala.math.BigInt;
import java.math.BigInteger;
import java.lang.reflect.InvocationTargetException;

%%

%class JFlexLexer
%unicode
%line
%column
%char
%public
%type Token
%function nextToken

%eofval{
    return new EofToken("", -1, -1);
%eofval}

%{

private <T extends Token> T makeToken(Class<T> type) {
    try {
        Constructor<T> constructor = type.getDeclaredConstructor(String.class, int.class, int.class);
        return constructor.newInstance(yytext(), yyline, yycolumn);
    } catch (NoSuchMethodException e) {
        throw new IllegalStateException(e);
    }
    catch (InvocationTargetException e) {
        throw new IllegalStateException(e);
    }
    catch (InstantiationException e) {
        throw new IllegalStateException(e);
    }
    catch (IllegalAccessException e) {
        throw new IllegalStateException(e);
    }
}

private Token makeNumberToken(String value) {
    BigInteger intValue = new BigInteger(value);
    return new DecNumberToken(yytext(), yyline, yycolumn, new BigInt(intValue));
}

private Token makeHexNumberToken(String value) {
    BigInteger intValue = new BigInteger(value.substring(2), 16);
    return new DecNumberToken(yytext(), yyline, yycolumn, new BigInt(intValue));
}

private IdToken makeIdToken() {
    return new IdToken(yytext(), yyline, yycolumn, yytext());
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
    "val"        { yybegin(AFTER); return makeToken(KwValToken.class); }
    "var"        { yybegin(AFTER); return makeToken(KwVarToken.class); }
    "def"        { yybegin(AFTER); return makeToken(KwDefToken.class); }
    "class"      { yybegin(AFTER); return makeToken(KwClassToken.class); }
    "interface"  { yybegin(AFTER); return makeToken(KwInterfaceToken.class); }
    "public"     { yybegin(AFTER); return makeToken(KwPublicToken.class); }
    "private"    { yybegin(AFTER); return makeToken(KwPrivateToken.class); }
    "final"      { yybegin(AFTER); return makeToken(KwFinalToken.class); }
    "extends"    { yybegin(AFTER); return makeToken(KwExtendsToken.class); }
    "implements" { yybegin(AFTER); return makeToken(KwImplementsToken.class); }
    "override"   { yybegin(AFTER); return makeToken(KwOverrideToken.class); }
    "as"         { yybegin(AFTER); return makeToken(KwAsToken.class); }
    "is"         { yybegin(AFTER); return makeToken(KwIsToken.class); }
    "this"       { yybegin(AFTER); return makeToken(KwThisToken.class); }
    "super"      { yybegin(AFTER); return makeToken(KwSuperToken.class); }

    // string
    \" { string.setLength(0);
         begin = yychar;
         line = yyline;
         column = yycolumn;
         yybegin(STRING); }

    // Identifier
    {Identifier} { yybegin(AFTER); return makeIdToken(); }

    // Integer
    {DecInteger} { yybegin(AFTER); return makeNumberToken(yytext()); }
    {HexInteger} { yybegin(AFTER); return makeHexNumberToken(yytext()); }
}

<YYINITIAL, AFTER> {
    ";" { resetAfterState(); return makeToken(SemicolonToken.class); }
    ":" { resetAfterState(); return makeToken(ColonToken.class); }
    "," { resetAfterState(); return makeToken(CommaToken.class); }

    // operators
    "=" { resetAfterState(); return makeToken(AssignToken.class); }
    "+" { resetAfterState(); return makeToken(PlusToken.class);}
    "-" { resetAfterState(); return makeToken(MinusToken.class); }
    "*" { resetAfterState(); return makeToken(TimesToken.class); }
    "/" { resetAfterState(); return makeToken(DivideToken.class); }

    "(" { resetAfterState(); return makeToken(OpenParenToken.class); }
    ")" { resetAfterState(); return makeToken(CloseParenToken.class); }
    "{" { resetAfterState(); return makeToken(OpenCurlyBraceToken.class); }
    "}" { resetAfterState(); return makeToken(CloseCurlyBraceToken.class); }

    // comments
    {Comment} { resetAfterState(); return makeToken(CommentToken.class); }

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
         return new StringToken("\"" + string.toString() + "\"", line, column, string.toString()); }

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
                                return makeToken(ErrorToken.class); }
    [^] { string.append(yytext()); }
}