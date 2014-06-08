package org.mc.idea_plugin

import org.mc.parser.ParserListener
import org.mc.lexer.{TokenPosition, Token}
import scala.collection.mutable

class McIdeaParserListener extends ParserListener {

    private val removedTokensIndicesSet = mutable.Set[Int]()

    def isErrorToken(tokenIndex: Int) : Boolean = removedTokensIndicesSet.contains(tokenIndex)

    override def onSyntaxError(unexpectedToken: Token): Unit = {
    }

    def onTokenRemovedOnErrorRecovery(removedToken: Token): Unit = {
        removedTokensIndicesSet.add(removedToken.position.index)
    }
}
