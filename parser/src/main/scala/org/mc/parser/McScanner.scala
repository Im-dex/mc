package org.mc.parser

import org.mc.lexer._
import java.io.InputStreamReader
import org.mc.parser.error.SkipTokenException
import beaver.Scanner
import beaver.Symbol
import scala.Some
import scala.Some
import scala.Some
import scala.Some
import scala.Some
import scala.Some
import scala.Some
import scala.Some
import scala.Some
import scala.Some
import scala.Some
import org.mc.lexer.TokenPosition
import scala.Some

object McScanner {
    def apply(reader: InputStreamReader) = {
        new McScanner(reader)
    }

    private val MAX_EOF_SEMICOLONS = 2
}

final class McScanner(val reader: InputStreamReader) extends Scanner
                                                     with Immutable {
    private val scanner = new McLexer(reader)
    private var eofPosition: Option[TokenPosition] = None
    private var eofSemicolonsCounter = 0

    @throws(classOf[Scanner.Exception])
    override def nextToken(): Symbol = {
        eofPosition match {
            case Some(position) => processEof()
            case None           => nextTokenImpl()
        }
    }

    private def nextTokenImpl(): Symbol = {
        val token = scanner.nextToken()

        token match {
            case Token.Eof(position) =>
                eofPosition = Some(position)
                eofSemicolonsCounter += 1
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

    private def processEof(): Symbol = {
        if (eofSemicolonsCounter < McScanner.MAX_EOF_SEMICOLONS) {
            eofSemicolonsCounter += 1
            new Symbol(Terminals.SEMICOLON, eofPosition.get)
        } else {
            new Symbol(Terminals.EOF, eofPosition.get)
        }
    }

    @throws(classOf[SkipTokenException])
    private def convertToken(token: Token): Symbol = {
        new Symbol(extractType(token), token.position)
    }

    @throws(classOf[SkipTokenException])
    private def extractType(token: Token): Short = token match {
        case Token.Id(_)        => Terminals.ID
        case Token.String(_)    => Terminals.STRING
        case Token.DecNumber(_) => Terminals.DEC_NUMBER

        case Token.Whitespace(_)        => throw SkipTokenException("Whitespace")
        case Token.Eof(_)               => Terminals.EOF
        case Token.MultiLineComment(_)  => throw SkipTokenException("Comment")
        case Token.SingleLineComment(_) => throw SkipTokenException("Comment")
        case Token.BadCharacter(_)      => throw new Scanner.Exception("error token")

        //case KwValToken(_)    => Terminals.VAL
        //case KwVarToken(_)    => Terminals.VAR

        case Token.Semicolon(_) => Terminals.SEMICOLON
        //case ColonToken(_)    => Terminals.COLON

        //case AssignToken(_)  => Terminals.ASSIGN
        case Token.Plus(_)     => Terminals.PLUS
        case Token.Minus(_)    => Terminals.MINUS
        case Token.Asterisk(_) => Terminals.TIMES
        case Token.Div(_)      => Terminals.DIVIDE

        case Token.OpenParen(_)        => Terminals.OPEN_PAREN
        case Token.CloseParen(_)       => Terminals.CLOSE_PAREN
        //case OpenCurlyBraceToken(_)  => Terminals.OPEN_CURLY_BRACE
        //case CloseCurlyBraceToken(_) => Terminals.CLOSE_CURLY_BRACE

        case _ => throw new IllegalArgumentException("Unknown token")
    }
}