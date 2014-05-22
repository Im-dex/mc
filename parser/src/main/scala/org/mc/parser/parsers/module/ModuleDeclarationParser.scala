package org.mc.parser.parsers.module

import org.mc.parser.{Ast, Scanner}
import org.mc.lexer.KwModuleToken
import org.mc.parser.parsers.Parser

object ModuleDeclarationParser {
    def apply(scanner: Scanner) = new ModuleDeclarationParser(scanner)
}

class ModuleDeclarationParser(scanner: Scanner) extends Parser {
    override def parse(): Option[Ast] = {
        scanner.lookahead() match {
            case KwModuleToken =>
                scanner.advanceToken()
                ModuleDeclarationNameParser().parse(scanner)
            case _ => None()
        }
    }
}
