package org.mc.idea_plugin

import com.intellij.lexer.LexerBase
import org.mc.lexer._
import com.intellij.psi.tree.IElementType
import java.io.StringReader
import org.mc.idea_plugin.psi.McTokenType
import scala.Some
import com.intellij.psi.TokenType

object McIdeaLexer {
    def apply(lexer: McLexer) = {
        new McIdeaLexer(lexer)
    }

    val ID: IElementType = new McTokenType("ID")
    val CHAR: IElementType = new McTokenType("CHAR")
    val STRING: IElementType = new McTokenType("STRING")
    val NUMBER: IElementType = new McTokenType("NUMBER")

    val MULTI_LINE_COMMENT: IElementType = new McTokenType("MULTILINE_COMMENT")
    val SINGLE_LINE_COMMENT: IElementType = new McTokenType("SINGLE_COMMENT")

    val KW_VAL: IElementType = new McTokenType("KW_VAL")
    val KW_VAR: IElementType = new McTokenType("KW_VAR")
    val KW_DEF: IElementType = new McTokenType("KW_DEF")
    val KW_CLASS: IElementType = new McTokenType("KW_CLASS")
    val KW_INTERFACE: IElementType = new McTokenType("KW_INTERFACE")
    val KW_PUBLIC: IElementType = new McTokenType("KW_PUBLIC")
    val KW_PRIVATE: IElementType = new McTokenType("KW_PRIVATE")
    val KW_FINAL: IElementType = new McTokenType("KW_FINAL")
    val KW_EXTENDS: IElementType = new McTokenType("KW_EXTENDS")
    val KW_IMPLEMENTS: IElementType = new McTokenType("KW_IMPLEMENTS")
    val KW_OVERRIDE: IElementType = new McTokenType("KW_OVERRIDE")
    val KW_AS: IElementType = new McTokenType("KW_AS")
    val KW_IS: IElementType = new McTokenType("KW_IS")
    val KW_THIS: IElementType = new McTokenType("KW_THIS")
    val KW_SUPER: IElementType = new McTokenType("KW_SUPER")

    val SEMICOLON: IElementType = new McTokenType("SEMICOLON")
    val COMMA: IElementType = new McTokenType("COMMA")

    val ASSIGN: IElementType = new McTokenType("ASSIGN")
    val PLUS: IElementType = new McTokenType("PLUS")
    val MINUS: IElementType = new McTokenType("MINUS")
    val ASTERISK: IElementType = new McTokenType("ASTERISK")
    val DIV: IElementType = new McTokenType("DIV")

    val OPEN_PAREN: IElementType = new McTokenType("OPEN_PAREN")
    val CLOSE_PAREN: IElementType = new McTokenType("CLOSE_PAREN")
    val OPEN_BRACE: IElementType = new McTokenType("OPEN_BRACE")
    val CLOSE_BRACE: IElementType = new McTokenType("CLOSE_BRACE")
}

class McIdeaLexer(val lexer: McLexer) extends LexerBase {

    private var currentToken: Option[Token] = None
    private var textBuffer: CharSequence = ""

    private var startOffset: Int = 0
    private var endOffset: Int = 0

    override def start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        textBuffer = buffer
        this.startOffset = startOffset
        this.endOffset = endOffset
        reset(initialState)
    }

    override def advance() {
        obtainNextToken()
    }

    override def getTokenEnd: Int = currentToken match {
        case Some(token) => startOffset + token.position.endOffset
        case _ =>
            obtainNextToken()
            getTokenEnd
    }

    override def getBufferEnd: Int = endOffset

    override def getTokenType: IElementType = currentToken match {
        case Some(token) => convertToken(token)
        case _ =>
            obtainNextToken()
            getTokenType
    }

    override def getBufferSequence: CharSequence = textBuffer

    override def getState: Int = lexer.yystate()

    override def getTokenStart: Int = currentToken match {
        case Some(token) => startOffset + token.position.beginOffset
        case None =>
            obtainNextToken()
            getTokenStart
    }

    private def obtainNextToken() {
        currentToken = Some(lexer.nextToken())
    }

    private def reset(initialState: Int) {
        val buffer = textBuffer.subSequence(startOffset, endOffset)
        val reader = new StringReader(buffer.toString)
        currentToken = None
        lexer.yyreset(reader)
        lexer.yybegin(initialState)
    }

    private def convertToken(token: Token): IElementType = token match {
        case _:Token.Id        => McIdeaLexer.ID
        case _:Token.Char      => McIdeaLexer.CHAR
        case _:Token.String    => McIdeaLexer.STRING
        case _:Token.DecNumber => McIdeaLexer.NUMBER
        case _:Token.HexNumber => McIdeaLexer.NUMBER
        case _:Token.BinNumber => McIdeaLexer.NUMBER
        case _:Token.Float     => McIdeaLexer.NUMBER
        case _:Token.HexFloat  => McIdeaLexer.NUMBER
        case _:Token.Double    => McIdeaLexer.NUMBER
        case _:Token.HexDouble => McIdeaLexer.NUMBER

        case _:Token.Whitespace        => TokenType.WHITE_SPACE
        case _:Token.Eof               => null
        case _:Token.MultiLineComment  => McIdeaLexer.MULTI_LINE_COMMENT
        case _:Token.SingleLineComment => McIdeaLexer.SINGLE_LINE_COMMENT
        case _:Token.BadCharacter      => TokenType.ERROR_ELEMENT

        case _:Token.KwVal        => McIdeaLexer.KW_VAL
        case _:Token.KwVar        => McIdeaLexer.KW_VAR
        case _:Token.KwDef        => McIdeaLexer.KW_DEF
        case _:Token.KwClass      => McIdeaLexer.KW_CLASS
        case _:Token.KwInterface  => McIdeaLexer.KW_INTERFACE
        case _:Token.KwPublic     => McIdeaLexer.KW_PUBLIC
        case _:Token.KwPrivate    => McIdeaLexer.KW_PRIVATE
        case _:Token.KwFinal      => McIdeaLexer.KW_FINAL
        case _:Token.KwExtends    => McIdeaLexer.KW_EXTENDS
        case _:Token.KwImplements => McIdeaLexer.KW_IMPLEMENTS
        case _:Token.KwOverride   => McIdeaLexer.KW_OVERRIDE
        case _:Token.KwAs         => McIdeaLexer.KW_AS
        case _:Token.KwIs         => McIdeaLexer.KW_IS
        case _:Token.KwThis       => McIdeaLexer.KW_THIS
        case _:Token.KwSuper      => McIdeaLexer.KW_SUPER

        case _:Token.Semicolon => McIdeaLexer.SEMICOLON
        case _:Token.Comma     => McIdeaLexer.COMMA

        case _:Token.Assign   => McIdeaLexer.ASSIGN
        case _:Token.Plus     => McIdeaLexer.PLUS
        case _:Token.Minus    => McIdeaLexer.MINUS
        case _:Token.Asterisk => McIdeaLexer.ASTERISK
        case _:Token.Div      => McIdeaLexer.DIV

        case _:Token.OpenParen  => McIdeaLexer.OPEN_PAREN
        case _:Token.CloseParen => McIdeaLexer.CLOSE_PAREN
        case _:Token.OpenBrace  => McIdeaLexer.OPEN_BRACE
        case _:Token.CloseBrace => McIdeaLexer.CLOSE_BRACE

        case _ => throw new IllegalArgumentException("Invalid token")
    }
}
