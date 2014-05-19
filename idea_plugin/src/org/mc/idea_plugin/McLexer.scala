package org.mc.idea_plugin

import com.intellij.lexer.LexerBase
import org.mc.lexer._
import com.intellij.psi.tree.IElementType
import java.io.StringReader
import org.mc.idea_plugin.psi.McTokenType
import org.mc.lexer.StringToken
import scala.Some
import org.mc.lexer.IdToken

object McLexer {
    def apply(lexer: JFlexLexer) = {
        new McLexer(lexer)
    }

    val ID: IElementType = new McTokenType("ID")
    val STRING: IElementType = new McTokenType("STRING")
    val DEC_NUMBER: IElementType = new McTokenType("DEC_NUMBER")

    //val EOF: IElementType = new McTokenType("EOF")
    val COMMENT: IElementType = new McTokenType("COMMENT")
    val ERROR: IElementType = new McTokenType("ERROR")

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
    val COLON: IElementType = new McTokenType("COLON")
    val COMMA: IElementType = new McTokenType("COMMA")

    val ASSIGN: IElementType = new McTokenType("ASSIGN")
    val PLUS: IElementType = new McTokenType("PLUS")
    val MINUS: IElementType = new McTokenType("MINUS")
    val TIMES: IElementType = new McTokenType("TIMES")
    val DIVIDE: IElementType = new McTokenType("DIVIDE")

    val OPEN_PAREN: IElementType = new McTokenType("OPEN_PAREN")
    val CLOSE_PAREN: IElementType = new McTokenType("CLOSE_PAREN")
    val OPEN_CURLY_BRACE: IElementType = new McTokenType("OPEN_CURLY_BRACE")
    val CLOSE_CURLY_BRACE: IElementType = new McTokenType("CLOSE_CURLY_BRACE")
}

class McLexer(val lexer: JFlexLexer) extends LexerBase {

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
        case Some(token) => token.position.endOffset
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
        case Some(token) => token.position.beginOffset
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
        lexer.yyreset(reader)
        lexer.yybegin(initialState)
    }

    private def convertToken(token: Token): IElementType = token match {
        case _: IdToken => McLexer.ID
        case _: StringToken => McLexer.STRING
        case _: DecNumberToken => McLexer.DEC_NUMBER

        case _: EofToken => null
        case _: CommentToken => McLexer.COMMENT
        case _: ErrorToken => McLexer.ERROR

        case _: KwValToken => McLexer.KW_VAL
        case _: KwVarToken => McLexer.KW_VAR
        case _: KwDefToken => McLexer.KW_DEF
        case _: KwClassToken => McLexer.KW_CLASS
        case _: KwInterfaceToken => McLexer.KW_INTERFACE
        case _: KwPublicToken => McLexer.KW_PUBLIC
        case _: KwPrivateToken => McLexer.KW_PRIVATE
        case _: KwFinalToken => McLexer.KW_FINAL
        case _: KwExtendsToken => McLexer.KW_EXTENDS
        case _: KwImplementsToken => McLexer.KW_IMPLEMENTS
        case _: KwOverrideToken => McLexer.KW_OVERRIDE
        case _: KwAsToken => McLexer.KW_AS
        case _: KwIsToken => McLexer.KW_IS
        case _: KwThisToken => McLexer.KW_THIS
        case _: KwSuperToken => McLexer.KW_SUPER

        case _: SemicolonToken => McLexer.SEMICOLON
        case _: ColonToken => McLexer.COLON
        case _: CommaToken => McLexer.COMMA

        case _: AssignToken => McLexer.ASSIGN
        case _: PlusToken => McLexer.PLUS
        case _: MinusToken => McLexer.MINUS
        case _: TimesToken => McLexer.TIMES
        case _: DivideToken => McLexer.DIVIDE

        case _: OpenParenToken => McLexer.OPEN_PAREN
        case _: CloseParenToken => McLexer.CLOSE_PAREN
        case _: OpenCurlyBraceToken => McLexer.OPEN_CURLY_BRACE
        case _: CloseCurlyBraceToken => McLexer.CLOSE_CURLY_BRACE

        case _ => throw new IllegalArgumentException("Invalid token")
    }
}
