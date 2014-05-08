package org.mc.lexer

sealed abstract class Token extends Immutable {
    val text: String
    val line: Int
    val column: Int
}

final case class IdToken(text: String, line: Int, column: Int, name: String) extends Token
final case class StringToken(text: String, line: Int, column: Int, value: String) extends Token
final case class DecNumberToken(text: String, line: Int, column: Int, value: BigInt) extends Token

final case class EofToken(text: String, line: Int, column: Int) extends Token
final case class CommentToken(text: String, line: Int, column: Int) extends Token
final case class ErrorToken(text: String, line: Int, column: Int) extends Token

final case class KwValToken(text: String, line: Int, column: Int) extends Token
final case class KwVarToken(text: String, line: Int, column: Int) extends Token

final case class SemicolonToken(text: String, line: Int, column: Int) extends Token
final case class ColonToken(text: String, line: Int, column: Int) extends Token

final case class AssignToken(text: String, line: Int, column: Int) extends Token
final case class PlusToken(text: String, line: Int, column: Int) extends Token
final case class MinusToken(text: String, line: Int, column: Int) extends Token
final case class TimesToken(text: String, line: Int, column: Int) extends Token
final case class DivideToken(text: String, line: Int, column: Int) extends Token

final case class OpenParenToken(text: String, line: Int, column: Int) extends Token
final case class CloseParenToken(text: String, line: Int, column: Int) extends Token
final case class OpenCurlyBraceToken(text: String, line: Int, column: Int) extends Token
final case class CloseCurlyBraceToken(text: String, line: Int, column: Int) extends Token