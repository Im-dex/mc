package org.mc.parser

import beaver.Parser.Events
import beaver.Symbol
import beaver.Scanner.Exception
import org.mc.lexer.TokenPosition

trait ParserListener extends Events {

    def onSyntaxError(unexpectedTokenPosition: TokenPosition): Unit

    def onTokenRemovedOnErrorRecovery(removedTokenPosition: TokenPosition): Unit

    override final def scannerError(e: Exception): Unit = super.scannerError(e)

    override final def syntaxError(token: Symbol): Unit = {
        val mcTokenPosition = token.value.asInstanceOf[TokenPosition]
        onSyntaxError(mcTokenPosition)
    }

    override final def unexpectedTokenRemoved(token: Symbol): Unit = {
        val mcTokenPosition = token.value.asInstanceOf[TokenPosition]
        onTokenRemovedOnErrorRecovery(mcTokenPosition)
    }

    // must not work
    override final def missingTokenInserted(token: Symbol): Unit = super.missingTokenInserted(token)

    // must not work
    override final def misspelledTokenReplaced(token: Symbol): Unit = super.misspelledTokenReplaced(token)

    override final def errorPhraseRemoved(error: Symbol): Unit = super.errorPhraseRemoved(error)
}
