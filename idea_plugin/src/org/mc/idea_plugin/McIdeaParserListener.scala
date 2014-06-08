package org.mc.idea_plugin

import org.mc.parser.ParserListener
import org.mc.lexer.{TokenPosition, Token}
import scala.collection.mutable

class McIdeaParserListener extends ParserListener {

    private val removedTokensSet = mutable.Set[TokenPosition]()

    override def onSyntaxError(unexpectedToken: Token): Unit = {
    }

    def onTokenRemovedOnErrorRecovery(removedToken: Token): Unit = {
        removedTokensSet.add(removedToken.position)
    }
}
