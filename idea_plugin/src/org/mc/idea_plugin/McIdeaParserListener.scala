package org.mc.idea_plugin

import org.mc.parser.ParserListener
import org.mc.lexer.TokenPosition
import scala.collection.mutable

class McIdeaParserListener extends ParserListener {

    private val removedTokensIndicesSet = mutable.Set[Int]()

    def isErrorToken(tokenIndex: Int) : Boolean = removedTokensIndicesSet.contains(tokenIndex)

    override def onSyntaxError(unexpectedTokenPosition: TokenPosition): Unit = {
    }

    def onTokenRemovedOnErrorRecovery(removedTokenPosition: TokenPosition): Unit = {
        removedTokensIndicesSet.add(removedTokenPosition.index)
    }
}
