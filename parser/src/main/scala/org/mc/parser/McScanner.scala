package org.mc.parser

import org.mc.lexer._
import org.mc.lexer.DecNumberToken
import org.mc.lexer.StringToken
import org.mc.lexer.IdToken
import java.io.InputStreamReader
import org.mc.parser.error.SkipTokenException
import beaver.Scanner
import beaver.Symbol

object McScanner {
    def apply(reader: InputStreamReader) = {
        new McScanner(reader)
    }
}

final class McScanner(val reader: InputStreamReader) extends Scanner
                                                     with Immutable {
    private val scanner = new McLexer(reader)
    private var isEof = false

    @throws(classOf[Scanner.Exception])
    override def nextToken(): Symbol = {
        isEof match {
            case true  => new Symbol(Terminals.EOF)
            case false => nextTokenImpl()
        }
    }

    private def nextTokenImpl(): Symbol = {
        val token = scanner.nextToken()

        token match {
            case _: EofToken =>
                isEof = true
                new Symbol(Terminals.MY_EOF, null)
            case _ =>
                try {
                    convertToken(token)
                }
                catch {
                    case _: SkipTokenException => nextTokenImpl()
                }
        }
    }

    @throws(classOf[SkipTokenException])
    private def convertToken(token: Token): Symbol = {
        new Symbol(extractType(token), token)
    }

    @throws(classOf[SkipTokenException])
    private def extractType(token: Token): Short = token match {
        case _: IdToken              => Terminals.ID
        case _: StringToken          => Terminals.STRING
        case _: DecNumberToken       => Terminals.DEC_NUMBER

        case _: EofToken             => Terminals.EOF
        case _: CommentToken         => throw SkipTokenException("Comment")
        case _: ErrorToken           => throw new Scanner.Exception("error token")

        //case _: KwValToken           => Terminals.VAL
        //case _: KwVarToken           => Terminals.VAR

        case _: SemicolonToken       => Terminals.SEMICOLON
        //case _: ColonToken           => Terminals.COLON

        //case _: AssignToken          => Terminals.ASSIGN
        case _: PlusToken            => Terminals.PLUS
        case _: MinusToken           => Terminals.MINUS
        case _: TimesToken           => Terminals.TIMES
        case _: DivideToken          => Terminals.DIVIDE

        case _: OpenParenToken       => Terminals.OPEN_PAREN
        case _: CloseParenToken      => Terminals.CLOSE_PAREN
        //case _: OpenCurlyBraceToken  => Terminals.OPEN_CURLY_BRACE
        //case _: CloseCurlyBraceToken => Terminals.CLOSE_CURLY_BRACE

        case _                    => throw new IllegalArgumentException("Unknown token")
    }
}