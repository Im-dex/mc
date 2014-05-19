package org.mc.lexer

sealed abstract class Token extends Immutable {
    val text: String
    val position: TokenPosition
}

final case class IdToken(text: String, position: TokenPosition, name: String) extends Token
final case class StringToken(text: String, position: TokenPosition, value: String) extends Token
final case class DecNumberToken(text: String, position: TokenPosition, value: BigInt) extends Token

final case class EofToken(text: String, position: TokenPosition) extends Token
final case class CommentToken(text: String, position: TokenPosition) extends Token
final case class ErrorToken(text: String, position: TokenPosition) extends Token

final case class KwValToken(text: String, position: TokenPosition) extends Token
final case class KwVarToken(text: String, position: TokenPosition) extends Token
final case class KwDefToken(text: String, position: TokenPosition) extends Token
final case class KwClassToken(text: String, position: TokenPosition) extends Token
final case class KwInterfaceToken(text: String, position: TokenPosition) extends Token
final case class KwPublicToken(text: String, position: TokenPosition) extends Token
final case class KwPrivateToken(text: String, position: TokenPosition) extends Token
final case class KwFinalToken(text: String, position: TokenPosition) extends Token
final case class KwExtendsToken(text: String, position: TokenPosition) extends Token
final case class KwImplementsToken(text: String, position: TokenPosition) extends Token
final case class KwOverrideToken(text: String, position: TokenPosition) extends Token
final case class KwAsToken(text: String, position: TokenPosition) extends Token
final case class KwIsToken(text: String, position: TokenPosition) extends Token
final case class KwThisToken(text: String, position: TokenPosition) extends Token
final case class KwSuperToken(text: String, position: TokenPosition) extends Token

final case class SemicolonToken(text: String, position: TokenPosition) extends Token
final case class ColonToken(text: String, position: TokenPosition) extends Token
final case class CommaToken(text: String, position: TokenPosition) extends Token

final case class AssignToken(text: String, position: TokenPosition) extends Token
final case class PlusToken(text: String, position: TokenPosition) extends Token
final case class MinusToken(text: String, position: TokenPosition) extends Token
final case class TimesToken(text: String, position: TokenPosition) extends Token
final case class DivideToken(text: String, position: TokenPosition) extends Token

final case class OpenParenToken(text: String, position: TokenPosition) extends Token
final case class CloseParenToken(text: String, position: TokenPosition) extends Token
final case class OpenCurlyBraceToken(text: String, position: TokenPosition) extends Token
final case class CloseCurlyBraceToken(text: String, position: TokenPosition) extends Token