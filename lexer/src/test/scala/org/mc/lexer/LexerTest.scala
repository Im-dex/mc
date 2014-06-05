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
        expectToken[KwVal]()
        expectToken[IdLiteral]()
        expectToken[Assign]()
        expectToken[HexNumberLiteral]()
        expectToken[Semicolon]()

        expectToken[MultiLineComment]()

        expectToken[KwVar]()
        expectToken[IdLiteral]()
        expectToken[Assign]()
        expectToken[StringLiteral]()
        expectToken[Semicolon]()

        expectToken[SingleLineComment]()

        expectToken[KwVar]()
        expectToken[IdLiteral]()
        expectToken[Assign]()
        expectToken[DecNumberLiteral]()
        expectToken[Semicolon]()

        expectToken[IdLiteral]()
        expectToken[BadCharacter]()
        expectToken[BadCharacter]()

        expectToken[SingleLineComment]()
        expectToken[DecNumberLiteral]()
        expectToken[Assign]()
        expectToken[DecNumberLiteral]()
        expectToken[Semicolon]()

        expectToken[SingleLineComment]()
        expectToken[DecNumberLiteral]()
        expectToken[IdLiteral]()
        expectToken[Semicolon]()

        expectToken[Plus]()
        expectToken[Minus]()
        expectToken[Asterisk]()
        expectToken[Div]()
        expectToken[Assign]()
        expectToken[Semicolon]()

        expectToken[SingleLineComment]()
        expectToken[DecNumberLiteral]()
        expectToken[IdLiteral]()

        expectToken[Eof]()
    }

    def expectToken[T <: Token : ClassTag](): Token = {
        val token = lexer.nextToken()

        token match {
            case Whitespace(_) =>
                expectToken[T]()
            case _ =>
                assume(token != null)

                val classOfT = implicitly[ClassTag[T]].runtimeClass
                assert(classOfT.isInstance(token))
                token
        }
    }
}