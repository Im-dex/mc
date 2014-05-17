package org.mc.lexer

final case class TokenPosition(line: Int, column: Int, beginOffset: Int, endOffset: Int) extends Immutable
