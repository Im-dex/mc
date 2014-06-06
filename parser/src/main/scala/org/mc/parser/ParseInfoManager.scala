package org.mc.parser

import beaver.Parser.Events
import beaver.Symbol
import beaver.Scanner.Exception
import java.util
import org.mc.lexer.{TokenPosition, Token}

final class ParseInfoManager extends Events {

    private val errors = new util.ArrayList[SyntaxErrorInfo]()
    private val removedOnRepair = new util.ArrayList[TokenPosition]()

    def getRemovedOnRepairTokensInfo = removedOnRepair

    override private def scannerError(e: Exception): Unit = super.scannerError(e)

    override private def syntaxError(token: Symbol): Unit = {
        errors.add(SyntaxErrorInfo("Unexpected symbol: ???"))
    }

    override private def unexpectedTokenRemoved(token: Symbol): Unit = {
        val mcToken = token.value.asInstanceOf[Token]
        removedOnRepair.add(mcToken.position)
    }

    // must not work
    override private def missingTokenInserted(token: Symbol): Unit = super.missingTokenInserted(token)

    // must not work
    override private def misspelledTokenReplaced(token: Symbol): Unit = super.misspelledTokenReplaced(token)

    override private def errorPhraseRemoved(error: Symbol): Unit = super.errorPhraseRemoved(error)
}
