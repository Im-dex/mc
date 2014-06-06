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
        case _: Token.Id        => Terminals.ID
        case _: Token.Char      => Terminals.CHAR
        case _: Token.String    => Terminals.STRING
        case _: Token.DecNumber => Terminals.DEC_NUMBER
        case _: Token.HexNumber => Terminals.HEX_NUMBER
        case _: Token.BinNumber => Terminals.BIN_NUMBER
        case _: Token.Float     => Terminals.FLOAT_NUMBER
        case _: Token.HexFloat  => Terminals.HEX_FLOAT_NUMBER
        case _: Token.Double    => Terminals.DOUBLE_NUMBER
        case _: Token.HexDouble => Terminals.HEX_DOUBLE_NUMBER

        case _: Token.Whitespace        => throw SkipTokenException("Whitespace")
        case _: Token.Eof               => Terminals.EOF
        case _: Token.MultiLineComment  => throw SkipTokenException("Comment")
        case _: Token.SingleLineComment => throw SkipTokenException("Comment")
        case _: Token.BadCharacter      => throw new Scanner.Exception("error token")

        //case _: Token.KwVal    => Terminals.VAL
        //case _: Token.KwVar    => Terminals.VAR
        //case _: Token.KwDef    => Terminals.DEF

        case _: Token.Semicolon => Terminals.SEMICOLON
        //case _: ColonToken    => Terminals.COLON

        //case _: AssignToken  => Terminals.ASSIGN
        case _: Token.Plus     => Terminals.PLUS
        case _: Token.Minus    => Terminals.MINUS
        case _: Token.Asterisk => Terminals.TIMES
        case _: Token.Div      => Terminals.DIVIDE

        case _: Token.OpenParen        => Terminals.OPEN_PAREN
        case _: Token.CloseParen       => Terminals.CLOSE_PAREN
        //case _: OpenCurlyBraceToken  => Terminals.OPEN_CURLY_BRACE
        //case _: CloseCurlyBraceToken => Terminals.CLOSE_CURLY_BRACE

        case _ => throw new IllegalArgumentException("Unknown token: " + token)
    }
}