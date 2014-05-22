package org.mc.parser.parsers

import org.mc.parser.{Ast, Scanner}
import org.mc.lexer.DecNumberToken
import scala.collection.mutable

object ExpressionParser {
    def apply() = new ExpressionParser
}

class ExpressionParser extends Parser {

    private val stack = mutable.Stack()

    override def parse(scanner: Scanner): Option[Ast] = {
        val token = scanner.nextToken()

        token match {
            case _: DecNumberToken =>
        }
    }
}
