package org.mc.parser

import org.mc.lexer.{StringToken, DecNumberToken, IdToken}

sealed abstract class Ast extends Immutable

final case class Block(expressions: List[Ast]) extends Ast
final case class ErrorExpression() extends Ast
final case class BinaryExpression(leftOperand: Ast, rightOperand: Ast, operator: Operator) extends Ast
final case class UnaryExpression(operand: Ast, operator: Option[UnaryOperator]) extends Ast

final case class IdConstant(token: IdToken) extends Ast
final case class DecConstant(token: DecNumberToken) extends Ast
final case class StringConstant(token: StringToken) extends Ast

final case class ValueDefinition(token: IdToken, init: Ast) extends Ast
final case class VariableDefinition(token: IdToken, init: Ast) extends Ast

final case class ClassDeclaration(name: String, modifiers: ClassModifiers, baseClass: Option[String],
                                  baseInterfaces: List[String]) extends Ast

final case class InterfaceDeclaration(name: String, accessModifier: AccessModifier,
                                      baseInterfaces: List[String]) extends Ast