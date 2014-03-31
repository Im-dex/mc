package org.mc.lexer

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.io.InputStreamReader

@RunWith(classOf[JUnitRunner])
class LexerTest extends FlatSpec {
    "Lexemes" should "be as expected" in {
        val resourceStream = this.getClass.getResourceAsStream("/lex_test.txt")
        val reader = new InputStreamReader(resourceStream)
        val lexer = new Lexer(reader)

        checkToken(lexer.nextToken(), TokenType.KwVal)
        checkId(lexer.nextToken(), "id")
        checkToken(lexer.nextToken(), TokenType.OpAssign)
        checkNumber(lexer.nextToken(), 0xf0)
    }

    def checkId(token: Token, name: String) {
        checkToken(token, TokenType.Id)
        assume(token.isInstanceOf[StringToken])
        assert(token.asInstanceOf[StringToken].value.equals(name))
    }

    def checkNumber(token: Token, value: BigInt) {
        checkToken(token, TokenType.Number)
        assume(token.isInstanceOf[NumberToken])
        
        val tokenValue : BigInt = token.asInstanceOf[NumberToken].value
        assert(tokenValue.equals(value))
    }

    def checkString(token: Token, value: String) {
        checkToken(token, TokenType.String)
        assume(token.isInstanceOf[StringToken])
        assert(token.asInstanceOf[StringToken].value.equals(value))
    }

    def checkToken(token: Token, tokenType: TokenType) {
        assume(token != null)
        assert(token.`type` == tokenType)
    }
}