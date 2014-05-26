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
        expectToken[IdToken]()
        expectToken[AssignToken]()
        expectToken[DecNumberToken]()
        expectToken[SemicolonToken]()

        expectToken[CommentToken]()

        expectToken[KwVarToken]()
        expectToken[IdToken]()
        expectToken[AssignToken]()
        expectToken[StringToken]()
        expectToken[SemicolonToken]()

        expectToken[CommentToken]()

        expectToken[KwVarToken]()
        expectToken[IdToken]()
        expectToken[AssignToken]()
        expectToken[DecNumberToken]()
        expectToken[SemicolonToken]()

        expectToken[IdToken]()
        expectToken[ErrorToken]()

        expectToken[CommentToken]()

        expectToken[DecNumberToken]()
        expectToken[ErrorToken]()
        expectToken[DecNumberToken]()
        expectToken[SemicolonToken]()

        expectToken[CommentToken]()

        expectToken[DecNumberToken]()
        expectToken[ErrorToken]()

        expectToken[PlusToken]()
        expectToken[MinusToken]()
        expectToken[TimesToken]()
        expectToken[DivideToken]()
        expectToken[AssignToken]()
        expectToken[SemicolonToken]()

        expectToken[EofToken]()
    }

    def expectToken[T <: Token : ClassTag](): Token = {
        val token = lexer.nextToken()

        token match {
            case (WhitespaceToken(_) | NewlineToken(_)) =>
                expectToken[T]()
            case _ =>
                assume(token != null)

                val classOfT = implicitly[ClassTag[T]].runtimeClass
                assert(classOfT.isInstance(token))
                token
        }
    }
}