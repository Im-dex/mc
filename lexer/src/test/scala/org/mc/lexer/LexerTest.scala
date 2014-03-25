package org.mc.lexer

import java.io.FileReader
import org.scalatest.FlatSpec

class LexerTest extends FlatSpec {
    "Lexemes" should "be as expected" in {
        val lexer = new Lexer(new FileReader("lex_test.txt"))

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
        assert(token.asInstanceOf[NumberToken].value.equals(value))
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