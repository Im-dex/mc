package org.mc.parser

import java_cup.runtime.Scanner
import java_cup.runtime.Symbol
import scala.util.parsing.input.StreamReader
import org.mc.lexer._
import org.mc.lexer.DecNumberToken
import org.mc.lexer.StringToken
import org.mc.lexer.IdToken
import java.io.InputStreamReader

object CupScanner {
    def apply(reader: InputStreamReader) = {
        new CupScanner(reader)
    }
}

final class CupScanner(val reader: InputStreamReader) extends Scanner
                                                      with Immutable {
    val scanner = new Lexer(reader)

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
    private def convertToken(scannerToken: Token): Symbol = {
        scannerToken match {
            case token: IdToken        => new Symbol(sym.ID, token)
            case token: StringToken    => new Symbol(sym.STRING, token)
            case token: DecNumberToken => new Symbol(sym.DEC_NUMBER, token)

            case _: EofToken           => new Symbol(sym.EOF)
            case _: CommentToken       => throw SkipTokenException("Comment")
            case _: ErrorToken         => new Symbol(sym.error)

            case _: KwValToken         => new Symbol(sym.VAL)
            case _: KwVarToken         => new Symbol(sym.VAR)

            case _: SemicolonToken     => new Symbol(sym.SEMICOLON)

            case _: AssignToken        => new Symbol(sym.ASSIGN)
            case _: PlusToken          => new Symbol(sym.PLUS)
            case _: MinusToken         => new Symbol(sym.MINUS)
            case _: TimesToken         => new Symbol(sym.TIMES)
            case _: DivideToken        => new Symbol(sym.DIVIDE)

            case _: LeftParenToken     => new Symbol(sym.LPAREN)
            case _: RightParenToken    => new Symbol(sym.RPAREN)

            case _                     => new Symbol(sym.error)
        }
    }
}