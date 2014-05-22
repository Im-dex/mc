package org.mc.parser.parsers

import org.mc.parser.Ast

private[parsers] trait Parser {
    def parse(): Option[Ast]
}
