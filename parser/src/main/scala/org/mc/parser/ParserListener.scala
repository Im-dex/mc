package org.mc.parser

import beaver.Parser.Events
import beaver.Symbol
import beaver.Scanner.Exception
import org.mc.lexer.Token

trait ParserListener extends Events {

    def onSyntaxError(unexpectedToken: Token): Unit

    def onTokenRemovedOnErrorRecovery(removedToken: Token): Unit

    override final def scannerError(e: Exception): Unit = super.scannerError(e)

    override final def syntaxError(token: Symbol): Unit = {
        val mcToken = token.value.asInstanceOf[Token]
        onSyntaxError(mcToken)
    }

    override final def unexpectedTokenRemoved(token: Symbol): Unit = {
        val mcToken = token.value.asInstanceOf[Token]
        onTokenRemovedOnErrorRecovery(mcToken)
    }

    // must not work
    override final def missingTokenInserted(token: Symbol): Unit = super.missingTokenInserted(token)

    // must not work
    override final def misspelledTokenReplaced(token: Symbol): Unit = super.misspelledTokenReplaced(token)

    override final def errorPhraseRemoved(error: Symbol): Unit = super.errorPhraseRemoved(error)
}
