package org.mc.lexer

abstract class Token extends Immutable {
    val startOffset: Int
    val endOffset: Int
    val line: Int
    val column: Int
}

case class IdToken(startOffset: Int, endOffset: Int, line: Int, column: Int, name: String) extends Token
case class StringToken(startOffset: Int, endOffset: Int, line: Int, column: Int, value: String) extends Token
case class DecNumberToken(startOffset: Int, endOffset: Int, line: Int, column: Int, value: BigInt) extends Token

case class EofToken(startOffset: Int, endOffset: Int, line: Int, column: Int) extends Token
case class CommentToken(startOffset: Int, endOffset: Int, line: Int, column: Int) extends Token
case class ErrorToken(startOffset: Int, endOffset: Int, line: Int, column: Int) extends Token

case class KwValToken(startOffset: Int, endOffset: Int, line: Int, column: Int) extends Token
case class KwVarToken(startOffset: Int, endOffset: Int, line: Int, column: Int) extends Token

case class SemicolonToken(startOffset: Int, endOffset: Int, line: Int, column: Int) extends Token

case class AssignToken(startOffset: Int, endOffset: Int, line: Int, column: Int) extends Token
case class PlusToken(startOffset: Int, endOffset: Int, line: Int, column: Int) extends Token
case class MinusToken(startOffset: Int, endOffset: Int, line: Int, column: Int) extends Token
case class TimesToken(startOffset: Int, endOffset: Int, line: Int, column: Int) extends Token
case class DivideToken(startOffset: Int, endOffset: Int, line: Int, column: Int) extends Token

case class LeftParenToken(startOffset: Int, endOffset: Int, line: Int, column: Int) extends Token
case class RightParenToken(startOffset: Int, endOffset: Int, line: Int, column: Int) extends Token