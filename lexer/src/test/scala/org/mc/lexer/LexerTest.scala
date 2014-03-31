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
        expectToken(TokenType.OpAssign)
        expectNumber(0xf0)

        expectComment()

        expectToken(TokenType.KwVar)
        expectId("test")
        expectToken(TokenType.OpAssign)
        expectString("String \t str")

        expectComment()

        expectToken(TokenType.KwVar)
        expectId("TEST")
        expectToken(TokenType.OpAssign)
        expectNumber(42)

        expectToken(TokenType.Eof)
    }

    def expectId(name: String) {
        val token = expectToken(TokenType.Id)
        assume(token.isInstanceOf[StringToken])
        assert(token.asInstanceOf[StringToken].value.equals(name))
    }

    def expectNumber(value: BigInt) {
        val token = expectToken(TokenType.Number)
        assume(token.isInstanceOf[NumberToken])

        val tokenValue : BigInt = token.asInstanceOf[NumberToken].value
        assert(tokenValue.equals(value))
    }

    def expectString(value: String) {
        val token = expectToken(TokenType.String)
        assume(token.isInstanceOf[StringToken])
        assert(token.asInstanceOf[StringToken].value.equals(value))
    }

    def expectComment() {
        expectToken(TokenType.Comment)
    }

    def expectToken(tokenType: TokenType) = {
        val token= lexer.nextToken()
        assume(token != null)
        assert(token.`type` == tokenType)
        token
    }
}