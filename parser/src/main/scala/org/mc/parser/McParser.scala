package org.mc.parser

import java_cup.runtime.Symbol
import org.mc.lexer.Token

object McParser {
    def apply(listener: McParserListener) = {
        new McParser(listener)
    }
}

class McParser(private val listener: McParserListener) extends Parser {


    override def unrecovered_syntax_error (cur_token: Symbol): Unit = {
        val token = cur_token.value.asInstanceOf[Token]
        listener.onFatalSyntaxError(token.line, token.column, token.text)
    }

    override def syntax_error (cur_token: Symbol): Unit = {
        val token = cur_token.value.asInstanceOf[Token]
        listener.onSyntaxError(token.line, token.column, token.text)
    }

    @throws(classOf[error.ParseException])
    override def parse (): Symbol = {
        try {
            super.parse()
        } catch {
            case exception: Exception => throw error.ParseException(exception)
        }
    }
}
