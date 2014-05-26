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
    private var eofPosition: Option[TokenPosition] = None

    @throws(classOf[Scanner.Exception])
    override def nextToken(): Symbol = {
        eofPosition match {
            case Some(position) => new Symbol(Terminals.EOF, position)
            case None           => nextTokenImpl()
        }
    }

    private def nextTokenImpl(): Symbol = {
        val token = scanner.nextToken()

        token match {
            case EofToken(position) =>
                eofPosition = Some(position)
                new Symbol(Terminals.SEMICOLON, position)
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
        new Symbol(extractType(token), token.position)
    }

    @throws(classOf[SkipTokenException])
    private def extractType(token: Token): Short = token match {
        case IdToken(_)        => Terminals.ID
        case StringToken(_)    => Terminals.STRING
        case DecNumberToken(_) => Terminals.DEC_NUMBER

        case WhitespaceToken(_) => throw SkipTokenException("Whitespace")
        case NewlineToken(_)    => throw SkipTokenException("Newline")
        case EofToken(_)        => Terminals.EOF
        case CommentToken(_)    => throw SkipTokenException("Comment")
        case ErrorToken(_)      => throw new Scanner.Exception("error token")

        //case KwValToken(_)    => Terminals.VAL
        //case KwVarToken(_)    => Terminals.VAR

        case SemicolonToken(_) => Terminals.SEMICOLON
        //case ColonToken(_)   => Terminals.COLON

        //case AssignToken(_) => Terminals.ASSIGN
        case PlusToken(_)     => Terminals.PLUS
        case MinusToken(_)    => Terminals.MINUS
        case TimesToken(_)    => Terminals.TIMES
        case DivideToken(_)   => Terminals.DIVIDE

        case OpenParenToken(_)         => Terminals.OPEN_PAREN
        case CloseParenToken(_)        => Terminals.CLOSE_PAREN
        //case OpenCurlyBraceToken(_)  => Terminals.OPEN_CURLY_BRACE
        //case CloseCurlyBraceToken(_) => Terminals.CLOSE_CURLY_BRACE

        case _ => throw new IllegalArgumentException("Unknown token")
    }
}