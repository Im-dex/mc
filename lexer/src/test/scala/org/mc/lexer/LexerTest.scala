package org.mc.lexer

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.io.InputStreamReader

@RunWith(classOf[JUnitRunner])
final class LexerTest extends FlatSpec {
    private val resourceStream = this.getClass.getResourceAsStream("/lex_test.txt")
    private val reader = new InputStreamReader(resourceStream)
    private val lexer = new Lexer(reader)

    "Lexemes" should "be as expected" in {
        expectToken(TokenType.KwVal)
        expectId("id")
        expectToken(TokenType.Assign)
        expectNumber(0xf0)
        expectSemicolon()

        expectComment()

        expectToken(TokenType.KwVar)
        expectId("test")
        expectToken(TokenType.Assign)
        expectString("String \t str")
        expectSemicolon()

        expectComment()

        expectToken(TokenType.KwVar)
        expectId("TEST")
        expectToken(TokenType.Assign)
        expectNumber(42)
        expectSemicolon()

        expectId("badId")
        expectError()

        expectComment()

        expectNumber(0)
        expectError()
        expectNumber(17)
        expectSemicolon()

        expectComment()

        expectNumber(11)
        expectError()

        expectToken(TokenType.Plus)
        expectToken(TokenType.Minus)
        expectToken(TokenType.Times)
        expectToken(TokenType.Divide)
        expectToken(TokenType.Assign)
        expectSemicolon()

        expectToken(TokenType.Eof)
    }

    def expectId(name: String) {
        val token = expectToken(TokenType.Id)
        assume(token.isInstanceOf[DataToken])
        assert(token.asInstanceOf[DataToken].value.equals(name))
    }

    def expectNumber(value: BigInt) {
        val token = expectToken(TokenType.Number)
        assume(token.isInstanceOf[DecNumberToken])

        val tokenValue : BigInt = token.asInstanceOf[DecNumberToken].value
        assert(tokenValue.equals(value))
    }

    def expectString(value: String) {
        val token = expectToken(TokenType.String)
        assume(token.isInstanceOf[DataToken])
        assert(token.asInstanceOf[DataToken].value.equals(value))
    }

    def expectComment() {
        expectToken(TokenType.Comment)
    }

    def expectError() {
        expectToken(TokenType.Error)
    }

    def expectSemicolon() {
        expectToken(TokenType.Semicolon)
    }

    def expectToken(tokenType: TokenType) = {
        val token = lexer.nextToken()
        assume(token != null)
        assert(token.`type` == tokenType)
        token
    }
}