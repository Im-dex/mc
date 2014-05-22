package org.mc.parser.parsers

import org.mc.parser.{Scanner, Ast}
import scala.collection.mutable

object ExpressionBlockParser {
    def apply() = new ExpressionBlockParser
}

class ExpressionBlockParser extends Parser {

    private val expressions = mutable.MutableList()

    override def parse(scanner: Scanner) : Option[Ast] = {
        val expressionParser = ExpressionParser()

        expressionParser.parse(scanner) match {
            case Some(expression) =>
                expressions += expression
                parse(scanner)
            case None =>
                expressions
        }
    }
}
