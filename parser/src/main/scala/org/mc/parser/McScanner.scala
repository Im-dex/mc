package org.mc.parser

import java_cup.runtime.Scanner
import java_cup.runtime.Symbol
import org.mc.lexer._
import org.mc.lexer.DecNumberToken
import org.mc.lexer.StringToken
import org.mc.lexer.IdToken
import java.io.InputStreamReader
import org.mc.parser.error.SkipTokenException

object McScanner {
    def apply(reader: InputStreamReader) = {
        new McScanner(reader)
    }
}

final class McScanner(val reader: InputStreamReader) extends Scanner
                                                     with Immutable {
    val scanner = new JFlexLexer(reader)

    @throws(classOf[Exception])
    override def next_token(): Symbol = {
        val token = scanner.nextToken()

        try {
            convertToken(token)
        }
        catch {
            case _: SkipTokenException => next_token()
        }
    }

    @throws(classOf[SkipTokenException])
    private[this] def convertToken(token: Token): Symbol = {
        new Symbol(extractType(token), token)
    }

    @throws(classOf[SkipTokenException])
    private[this] def extractType(token: Token): Int = token match {
        case _: IdToken              => sym.ID
        case _: StringToken          => sym.STRING
        case _: DecNumberToken       => sym.DEC_NUMBER

        case _: EofToken             => sym.EOF
        case _: CommentToken         => throw SkipTokenException("Comment")
        case _: ErrorToken           => sym.error

        case _: KwValToken           => sym.VAL
        case _: KwVarToken           => sym.VAR

        case _: SemicolonToken       => sym.SEMICOLON
        case _: ColonToken           => sym.COLON

        case _: AssignToken          => sym.ASSIGN
        case _: PlusToken            => sym.PLUS
        case _: MinusToken           => sym.MINUS
        case _: TimesToken           => sym.TIMES
        case _: DivideToken          => sym.DIVIDE

        case _: OpenParenToken       => sym.OPEN_PAREN
        case _: CloseParenToken      => sym.CLOSE_PAREN
        case _: OpenCurlyBraceToken  => sym.OPEN_CURLY_BRACE
        case _: CloseCurlyBraceToken => sym.CLOSE_CURLY_BRACE

        case _                       => sym.error
    }
}