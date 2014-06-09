package org.mc.lexer

object Token {
    final case class DecNumber(position: TokenPosition) extends Token
    final case class HexNumber(position: TokenPosition) extends Token
    final case class BinNumber(position: TokenPosition) extends Token
    final case class Float(position: TokenPosition) extends Token
    final case class HexFloat(position: TokenPosition) extends Token
    final case class Double(position: TokenPosition) extends Token
    final case class HexDouble(position: TokenPosition) extends Token
    final case class Id(position: TokenPosition) extends Token
    final case class Char(position: TokenPosition) extends Token
    final case class String(position: TokenPosition) extends Token

    final case class Whitespace(position: TokenPosition) extends Token
    final case class Eof(position: TokenPosition) extends Token
    final case class MultiLineComment(position: TokenPosition) extends Token
    final case class SingleLineComment(position: TokenPosition) extends Token
    final case class BadCharacter(position: TokenPosition) extends Token

    final case class KwVal(position: TokenPosition) extends Token
    final case class KwVar(position: TokenPosition) extends Token
    final case class KwDef(position: TokenPosition) extends Token
    final case class KwClass(position: TokenPosition) extends Token
    final case class KwInterface(position: TokenPosition) extends Token
    final case class KwPublic(position: TokenPosition) extends Token
    final case class KwPrivate(position: TokenPosition) extends Token
    final case class KwProtected(position: TokenPosition) extends Token
    final case class KwFinal(position: TokenPosition) extends Token
    final case class KwExtends(position: TokenPosition) extends Token
    final case class KwImplements(position: TokenPosition) extends Token
    final case class KwOverride(position: TokenPosition) extends Token
    final case class KwAs(position: TokenPosition) extends Token
    final case class KwIs(position: TokenPosition) extends Token
    final case class KwThis(position: TokenPosition) extends Token
    final case class KwSuper(position: TokenPosition) extends Token
    final case class KwModule(position: TokenPosition) extends Token
    final case class KwImport(position: TokenPosition) extends Token

    final case class Semicolon(position: TokenPosition) extends Token
    final case class Dot(position: TokenPosition) extends Token
    final case class Comma(position: TokenPosition) extends Token
    final case class OpenParen(position: TokenPosition) extends Token
    final case class CloseParen(position: TokenPosition) extends Token
    final case class OpenBrace(position: TokenPosition) extends Token
    final case class CloseBrace(position: TokenPosition) extends Token

    final case class Assign(position: TokenPosition) extends Token
    final case class Plus(position: TokenPosition) extends Token
    final case class Minus(position: TokenPosition) extends Token
    final case class Asterisk(position: TokenPosition) extends Token
    final case class Div(position: TokenPosition) extends Token
}

sealed abstract class Token extends Immutable {
    val position: TokenPosition
}