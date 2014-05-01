package org.mc.lexer

sealed abstract class Token extends Immutable {
    val text: String
    val line: Int
    val column: Int
}

case class IdToken(text: String, line: Int, column: Int, name: String) extends Token
case class StringToken(text: String, line: Int, column: Int, value: String) extends Token
case class DecNumberToken(text: String, line: Int, column: Int, value: BigInt) extends Token

case class EofToken(text: String, line: Int, column: Int) extends Token
case class CommentToken(text: String, line: Int, column: Int) extends Token
case class ErrorToken(text: String, line: Int, column: Int) extends Token

case class KwValToken(text: String, line: Int, column: Int) extends Token
case class KwVarToken(text: String, line: Int, column: Int) extends Token

case class SemicolonToken(text: String, line: Int, column: Int) extends Token
case class ColonToken(text: String, line: Int, column: Int) extends Token

case class AssignToken(text: String, line: Int, column: Int) extends Token
case class PlusToken(text: String, line: Int, column: Int) extends Token
case class MinusToken(text: String, line: Int, column: Int) extends Token
case class TimesToken(text: String, line: Int, column: Int) extends Token
case class DivideToken(text: String, line: Int, column: Int) extends Token

case class LeftParenToken(text: String, line: Int, column: Int) extends Token
case class RightParenToken(text: String, line: Int, column: Int) extends Token