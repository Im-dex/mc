package org.mc.parser

import org.mc.lexer.{StringToken, DecNumberToken, IdToken}
import beaver.Symbol

sealed abstract class Ast extends Symbol with Immutable

sealed abstract class Expression extends Ast with Immutable
//sealed abstract class Statement extends Ast with Immutable

sealed abstract class BinaryExpression(val left: Expression, val right: Expression) extends Expression with Immutable
sealed abstract class UnaryExpression(val expression: Expression) extends Expression with Immutable
sealed abstract class Literal extends Expression with Immutable

//=========================================================================================================

final case class ExpressionList(expressions: List[Expression]) extends Ast with Immutable
final case class EmptyExpression() extends Expression with Immutable
final case class ErrorExpression() extends Expression with Immutable

final case class AddExpression(override val left: Expression, override val right: Expression) extends BinaryExpression(left, right) with Immutable
final case class SubExpression(override val left: Expression, override val right: Expression) extends BinaryExpression(left, right) with Immutable
final case class MulExpression(override val left: Expression, override val right: Expression) extends BinaryExpression(left, right) with Immutable
final case class DivExpression(override val left: Expression, override val right: Expression) extends BinaryExpression(left, right) with Immutable
final case class MinusExpression(override val expression: Expression) extends UnaryExpression(expression) with Immutable

final case class IdLiteral(token: IdToken) extends Literal with Immutable
final case class DecNumberLiteral(token: DecNumberToken) extends Literal with Immutable
final case class StringLiteral(token: StringToken) extends Literal with Immutable

//final case class ValueDefinition(token: IdToken, init: Ast) extends Ast
//final case class VariableDefinition(token: IdToken, init: Ast) extends Ast

//final case class ClassDeclaration(name: String, modifiers: ClassModifiers, inheritanceInfo: ClassInheritanceInfo) extends Ast
//final case class InterfaceDeclaration(name: String, accessModifier: AccessModifier, baseInterfaces: List[String]) extends Ast