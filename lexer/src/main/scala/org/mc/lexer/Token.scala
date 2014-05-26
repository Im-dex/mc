package org.mc.lexer

sealed abstract class Token extends Immutable {
    val position: TokenPosition
}

final case class IdToken(position: TokenPosition) extends Token
final case class StringToken(position: TokenPosition) extends Token
final case class DecNumberToken(position: TokenPosition) extends Token

final case class WhitespaceToken(position: TokenPosition) extends Token
final case class NewlineToken(position: TokenPosition) extends Token
final case class EofToken(position: TokenPosition) extends Token
final case class CommentToken(position: TokenPosition) extends Token
final case class ErrorToken(position: TokenPosition) extends Token

final case class KwValToken(position: TokenPosition) extends Token
final case class KwVarToken(position: TokenPosition) extends Token
final case class KwDefToken(position: TokenPosition) extends Token
final case class KwClassToken(position: TokenPosition) extends Token
final case class KwInterfaceToken(position: TokenPosition) extends Token
final case class KwPublicToken(position: TokenPosition) extends Token
final case class KwPrivateToken(position: TokenPosition) extends Token
final case class KwFinalToken(position: TokenPosition) extends Token
final case class KwExtendsToken(position: TokenPosition) extends Token
final case class KwImplementsToken(position: TokenPosition) extends Token
final case class KwOverrideToken(position: TokenPosition) extends Token
final case class KwAsToken(position: TokenPosition) extends Token
final case class KwIsToken(position: TokenPosition) extends Token
final case class KwThisToken(position: TokenPosition) extends Token
final case class KwSuperToken(position: TokenPosition) extends Token

final case class SemicolonToken(position: TokenPosition) extends Token
final case class ColonToken(position: TokenPosition) extends Token
final case class CommaToken(position: TokenPosition) extends Token

final case class AssignToken(position: TokenPosition) extends Token
final case class PlusToken(position: TokenPosition) extends Token
final case class MinusToken(position: TokenPosition) extends Token
final case class TimesToken(position: TokenPosition) extends Token
final case class DivideToken(position: TokenPosition) extends Token

final case class OpenParenToken(position: TokenPosition) extends Token
final case class CloseParenToken(position: TokenPosition) extends Token
final case class OpenCurlyBraceToken(position: TokenPosition) extends Token
final case class CloseCurlyBraceToken(position: TokenPosition) extends Token