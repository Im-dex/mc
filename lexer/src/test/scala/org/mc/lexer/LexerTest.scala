package org.mc.lexer

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.io.InputStreamReader
import scala.reflect.ClassTag

@RunWith(classOf[JUnitRunner])
final class LexerTest extends FlatSpec {
    private val resourceStream = this.getClass.getResourceAsStream("/lex_test.txt")
    private val reader = new InputStreamReader(resourceStream)
    private val lexer = new McLexer(reader)

    "Lexemes" should "be as expected" in {
        expectToken[Token.KwVal]()
        expectToken[Token.Id]()
        expectToken[Token.Assign]()
        expectToken[Token.HexNumber]()
        expectToken[Token.Semicolon]()

        expectToken[Token.MultiLineComment]()

        expectToken[Token.KwVar]()
        expectToken[Token.Id]()
        expectToken[Token.Assign]()
        expectToken[Token.String]()
        expectToken[Token.Semicolon]()

        expectToken[Token.SingleLineComment]()

        expectToken[Token.KwVar]()
        expectToken[Token.Id]()
        expectToken[Token.Assign]()
        expectToken[Token.DecNumber]()
        expectToken[Token.Semicolon]()

        expectToken[Token.Id]()
        expectToken[Token.BadCharacter]()
        expectToken[Token.BadCharacter]()

        expectToken[Token.SingleLineComment]()
        expectToken[Token.DecNumber]()
        expectToken[Token.Assign]()
        expectToken[Token.DecNumber]()
        expectToken[Token.Semicolon]()

        expectToken[Token.SingleLineComment]()
        expectToken[Token.DecNumber]()
        expectToken[Token.Id]()
        expectToken[Token.Semicolon]()

        expectToken[Token.Plus]()
        expectToken[Token.Minus]()
        expectToken[Token.Asterisk]()
        expectToken[Token.Div]()
        expectToken[Token.Assign]()
        expectToken[Token.Semicolon]()

        expectToken[Token.SingleLineComment]()
        expectToken[Token.DecNumber]()
        expectToken[Token.Id]()

        expectToken[Token.Eof]()
    }

    def expectToken[T <: Token : ClassTag](): Token = {
        val token = lexer.nextToken()

        token match {
            case Token.Whitespace(_) =>
                expectToken[T]()
            case _ =>
                assume(token != null)

                val classOfT = implicitly[ClassTag[T]].runtimeClass
                assert(classOfT.isInstance(token))
                token
        }
    }
}