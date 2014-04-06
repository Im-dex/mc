package org.mc.parser

import org.mc.lexer.{StringToken, DecNumberToken, IdToken}

abstract class Ast extends Immutable

case class BinaryExpression(leftOperand: Ast, rightOperand: Ast, operator: Operator) extends Ast
case class UnaryExpression(operand: Ast, operator: Option[UnaryOperator]) extends Ast
case class IdConstant(token: IdToken) extends Ast
case class DecConstant(token: DecNumberToken) extends Ast
case class StringConstant(token: StringToken) extends Ast