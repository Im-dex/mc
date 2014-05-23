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
                new Symbol(Terminals.SEMICOLON, null)
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
        case IdToken(_,_,_)            => Terminals.ID
        case StringToken(_,_,_)        => Terminals.STRING
        case DecNumberToken(_,_,_)     => Terminals.DEC_NUMBER

        case WhitespaceToken(_,_)      => throw SkipTokenException("Whitespace")
        case NewlineToken(_,_)         => throw SkipTokenException("Newline")
        case EofToken(_,_)             => Terminals.EOF
        case CommentToken(_,_)         => throw SkipTokenException("Comment")
        case ErrorToken(_,_)           => throw new Scanner.Exception("error token")

        //case KwValToken(_,_)           => Terminals.VAL
        //case KwVarToken(_,_)           => Terminals.VAR

        case SemicolonToken(_,_)       => Terminals.SEMICOLON
        //case ColonToken(_,_)           => Terminals.COLON

        //case AssignToken(_,_)          => Terminals.ASSIGN
        case PlusToken(_,_)            => Terminals.PLUS
        case MinusToken(_,_)           => Terminals.MINUS
        case TimesToken(_,_)           => Terminals.TIMES
        case DivideToken(_,_)          => Terminals.DIVIDE

        case OpenParenToken(_,_)       => Terminals.OPEN_PAREN
        case CloseParenToken(_,_)      => Terminals.CLOSE_PAREN
        //case OpenCurlyBraceToken(_,_)  => Terminals.OPEN_CURLY_BRACE
        //case CloseCurlyBraceToken(_,_) => Terminals.CLOSE_CURLY_BRACE

        case _                    => throw new IllegalArgumentException("Unknown token")
    }
}