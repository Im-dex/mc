package org.mc.parser

import org.mc.lexer.{StringToken, DecNumberToken, IdToken}
import beaver.Symbol

sealed abstract class Ast extends Symbol with Immutable

sealed abstract class Expression extends Ast with Immutable
//sealed abstract class Statement extends Ast with Immutable

sealed abstract class BinaryExpression extends Expression with Immutable {
    val left: Expression
    val right: Expression
}

sealed abstract class UnaryExpression extends Expression with Immutable {
    val expression: Expression
}

sealed abstract class Literal extends Expression with Immutable

//=========================================================================================================

final case class ExpressionList(expressions: List[Expression]) extends Ast with Immutable
final case class EmptyExpression() extends Expression with Immutable
final case class ErrorExpression(beforeTokenIndex: Int) extends Expression with Immutable
final case class ParenthesizedExpression(expression: Expression) extends Expression with Immutable

final case class AddExpression(left: Expression, right: Expression) extends BinaryExpression with Immutable
final case class SubExpression(left: Expression, right: Expression) extends BinaryExpression with Immutable
final case class MulExpression(left: Expression, right: Expression) extends BinaryExpression with Immutable
final case class DivExpression(left: Expression, right: Expression) extends BinaryExpression with Immutable
final case class MinusExpression(expression: Expression) extends UnaryExpression with Immutable

final case class IdLiteral() extends Literal with Immutable
final case class DecNumberLiteral() extends Literal with Immutable
final case class StringLiteral() extends Literal with Immutable

//final case class ValueDefinition(token: IdToken, init: Ast) extends Ast
//final case class VariableDefinition(token: IdToken, init: Ast) extends Ast

//final case class ClassDeclaration(name: String, modifiers: ClassModifiers, inheritanceInfo: ClassInheritanceInfo) extends Ast
//final case class InterfaceDeclaration(name: String, accessModifier: AccessModifier, baseInterfaces: List[String]) extends Ast