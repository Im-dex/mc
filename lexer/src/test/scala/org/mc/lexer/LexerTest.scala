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
        expectToken[KwValToken]()
        expectId("id")
        expectToken[AssignToken]()
        expectNumber(0xf0)
        expectToken[SemicolonToken]()

        expectToken[CommentToken]()

        expectToken[KwVarToken]()
        expectId("test")
        expectToken[AssignToken]()
        expectString("String \t str")
        expectToken[SemicolonToken]()

        expectToken[CommentToken]()

        expectToken[KwVarToken]()
        expectId("TEST")
        expectToken[AssignToken]()
        expectNumber(42)
        expectToken[SemicolonToken]()

        expectId("badId")
        expectToken[ErrorToken]()

        expectToken[CommentToken]()

        expectNumber(0)
        expectToken[ErrorToken]()
        expectNumber(17)
        expectToken[SemicolonToken]()

        expectToken[CommentToken]()

        expectNumber(11)
        expectToken[ErrorToken]()

        expectToken[PlusToken]()
        expectToken[MinusToken]()
        expectToken[TimesToken]()
        expectToken[DivideToken]()
        expectToken[AssignToken]()
        expectToken[SemicolonToken]()

        expectToken[EofToken]()
    }

    def expectId(name: String) {
        val token = expectToken[IdToken]()
        assert(token.asInstanceOf[IdToken].name.equals(name))
    }

    def expectNumber(value: BigInt) {
        val token = expectToken[DecNumberToken]()
        assert(token.asInstanceOf[DecNumberToken].value.equals(value))
    }

    def expectString(value: String) {
        val token = expectToken[StringToken]()
        assert(token.asInstanceOf[StringToken].value.equals(value))
    }

    def expectToken[T <: Token : ClassTag](): Token = {
        val token = lexer.nextToken()

        token match {
            case (WhitespaceToken(_,_) | NewlineToken(_,_)) =>
                expectToken[T]()
            case _ =>
                assume(token != null)

                val classOfT = implicitly[ClassTag[T]].runtimeClass
                assert(classOfT.isInstance(token))
                token
        }
    }
}