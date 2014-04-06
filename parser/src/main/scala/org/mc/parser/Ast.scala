package org.mc.parser

import scala.collection.immutable.Seq
import org.mc.lexer.{StringToken, DecNumberToken, IdToken}

abstract class Ast extends Immutable

final case class ExpressionList(children: Seq[Ast]) extends Ast
final case class ErrorExpression(/*children: Seq[Ast]*/) extends Ast
final case class BinaryExpression(leftOperand: Ast, rightOperand: Ast, operator: Operator) extends Ast
final case class UnaryExpression(operand: Ast, operator: Option[UnaryOperator]) extends Ast

final case class IdConstant(token: IdToken) extends Ast
final case class DecConstant(token: DecNumberToken) extends Ast
final case class StringConstant(token: StringToken) extends Ast

final case class ValueDefinition(token: IdToken, init: Ast) extends Ast
final case class VariableDefinition(token: IdToken, init: Ast) extends Ast