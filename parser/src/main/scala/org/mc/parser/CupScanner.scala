package org.mc.parser

import java_cup.runtime.Scanner
import java_cup.runtime.Symbol
import scala.util.parsing.input.StreamReader
import org.mc.lexer._
import org.mc.lexer.DecNumberToken
import org.mc.lexer.StringToken
import org.mc.lexer.IdToken

final class CupScanner(val reader: StreamReader) extends Scanner
                                                 with Immutable {
    val scanner = new Lexer(reader)

    @throws(classOf[Exception])
    override def next_token(): Symbol = {
        val lexToken = scanner.nextToken()

        try {
            convertToken(lexToken)
        }
        catch {
            case SkipTokenException => next_token()
        }
    }

    @throws(classOf[SkipTokenException])
    private def convertToken(token: Token): Symbol = {
        lexToken match {
            case token: IdToken        => new Symbol(sym.ID, token)
            case token: StringToken    => new Symbol(sym.STRING, token)
            case token: DecNumberToken => new Symbol(sym.DEC_NUMBER, token)

            case EofToken              => new Symbol(sym.EOF)
            case CommentToken          => throw SkipTokenException("Comment")
            case ErrorToken            => new Symbol(sym.error)

            case KwValToken            => new Symbol(sym.VAL)
            case KwVarToken            => new Symbol(sym.VAR)

            case SemicolonToken        => new Symbol(sym.SEMICOLON)

            case AssignToken           => new Symbol(sym.ASSIGN)
            case PlusToken             => new Symbol(sym.PLUS)
            case MinusToken            => new Symbol(sym.MINUS)
            case TimesToken            => new Symbol(sym.TIMES)
            case DivideToken           => new Symbol(sym.DIVIDE)

            case LeftParenToken        => new Symbol(sym.LPAREN)
            case RightParenToken       => new Symbol(sym.RPAREN)

            case _                     => throw InvalidArgumentException()
        }
    }
}