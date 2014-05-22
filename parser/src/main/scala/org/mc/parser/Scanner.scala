package org.mc.parser

import org.mc.lexer.Token

trait Scanner {
    def advanceToken(): Unit

    def lookahead(): Token

    def currentToken(): Token
}
