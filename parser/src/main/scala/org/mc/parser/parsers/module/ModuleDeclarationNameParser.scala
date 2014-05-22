package org.mc.parser.parsers.module

import org.mc.parser.parsers.Parser
import org.mc.parser.{ErrorExpression, Ast, Scanner}
import org.mc.lexer.IdToken

object ModuleDeclarationNameParser {
    def apply(scanner: Scanner) = new ModuleDeclarationNameParser(scanner)
}

final class ModuleDeclarationNameParser(scanner: Scanner) extends Parser {
    override def parse(): Option[Ast] = scanner.currentToken() match {
        case IdToken =>
            scanner.advanceToken()
            parse()
        case _
    }

    private def recoverError(): ErrorExpression = {

    }
}
