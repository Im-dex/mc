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
    return makeSimpleToken(EofToken.class);
%eofval}

%{

private int getTokenEndOffset() {
    return yytext().length() - yychar;
}

private TokenPosition calcTokenPosition(int line, int column) {
    return new TokenPosition(line, column, yychar, getTokenEndOffset(), tokenIndex++);
}

private <T extends Token> T makeSimpleToken(Class<T> type) {
    TokenPosition tokenPosition = calcTokenPosition(yyline, yycolumn);
    return makeToken(type, tokenPosition);
}

private <T extends Token> T makeExtraToken(Class<T> type) {
    TokenPosition tokenPosition = calcTokenPosition(extraLine, extraColumn);
    return makeToken(type, tokenPosition);
}

private <T extends Token> T makeToken(Class<T> type, TokenPosition position) {
    try {
        Constructor<T> constructor = type.getDeclaredConstructor(TokenPosition.class);
        return constructor.newInstance(position);
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

private void resetAfterState() {
    if (yystate() == AFTER) {
        yybegin(YYINITIAL);
    }
}

private int extraLine = -1;
private int extraColumn = -1;

private int tokenIndex = 0;

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
    "val"        { yybegin(AFTER); return makeSimpleToken(KwValToken.class); }
    "var"        { yybegin(AFTER); return makeSimpleToken(KwVarToken.class); }
    "def"        { yybegin(AFTER); return makeSimpleToken(KwDefToken.class); }
    "class"      { yybegin(AFTER); return makeSimpleToken(KwClassToken.class); }
    "interface"  { yybegin(AFTER); return makeSimpleToken(KwInterfaceToken.class); }
    "public"     { yybegin(AFTER); return makeSimpleToken(KwPublicToken.class); }
    "private"    { yybegin(AFTER); return makeSimpleToken(KwPrivateToken.class); }
    "final"      { yybegin(AFTER); return makeSimpleToken(KwFinalToken.class); }
    "extends"    { yybegin(AFTER); return makeSimpleToken(KwExtendsToken.class); }
    "implements" { yybegin(AFTER); return makeSimpleToken(KwImplementsToken.class); }
    "override"   { yybegin(AFTER); return makeSimpleToken(KwOverrideToken.class); }
    "as"         { yybegin(AFTER); return makeSimpleToken(KwAsToken.class); }
    "is"         { yybegin(AFTER); return makeSimpleToken(KwIsToken.class); }
    "this"       { yybegin(AFTER); return makeSimpleToken(KwThisToken.class); }
    "super"      { yybegin(AFTER); return makeSimpleToken(KwSuperToken.class); }

    // string
    \" { extraLine = yyline;
         extraColumn = yycolumn;
         yybegin(STRING); }

    // Identifier
    {Identifier} { yybegin(AFTER); return makeSimpleToken(IdToken.class); }

    // Integer
    {DecInteger}|{HexInteger} { yybegin(AFTER); return makeSimpleToken(DecNumberToken.class); }
}

<YYINITIAL, AFTER> {
    ";" { resetAfterState(); return makeSimpleToken(SemicolonToken.class); }
    ":" { resetAfterState(); return makeSimpleToken(ColonToken.class); }
    "," { resetAfterState(); return makeSimpleToken(CommaToken.class); }

    // operators
    "=" { resetAfterState(); return makeSimpleToken(AssignToken.class); }
    "+" { resetAfterState(); return makeSimpleToken(PlusToken.class);}
    "-" { resetAfterState(); return makeSimpleToken(MinusToken.class); }
    "*" { resetAfterState(); return makeSimpleToken(TimesToken.class); }
    "/" { resetAfterState(); return makeSimpleToken(DivideToken.class); }

    "(" { resetAfterState(); return makeSimpleToken(OpenParenToken.class); }
    ")" { resetAfterState(); return makeSimpleToken(CloseParenToken.class); }
    "{" { resetAfterState(); return makeSimpleToken(OpenCurlyBraceToken.class); }
    "}" { resetAfterState(); return makeSimpleToken(CloseCurlyBraceToken.class); }

    // comments
    {Comment} { resetAfterState(); return makeSimpleToken(CommentToken.class); }

    // whitespace
    {WhiteSpace} { resetAfterState(); return makeSimpleToken(WhitespaceToken.class); }
    {LineEnding} { resetAfterState(); return makeSimpleToken(NewlineToken.class); }

    [^] { extraLine = yyline;
          extraColumn = yycolumn;
          yybegin(ERROR); }
}

<STRING> {
    \" { yybegin(YYINITIAL);
         return makeExtraToken(StringToken.class); }

    [^\n\r\"\\]+    {  }
    \\t             {  }
    \\n             {  }

    \\r             {  }
    \\\"            {  }
    \\              {  }

    //TODO: {LineEnding}    { yybegin(ERROR); }
}

<ERROR> {
    {WhiteSpace}|{LineEnding} { yybegin(YYINITIAL);
                                return makeExtraToken(ErrorToken.class); }
    [^] {  }
}