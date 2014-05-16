package org.mc.parser

abstract class Operator extends Immutable
abstract class UnaryOperator extends Operator

abstract class PrefixUnaryOperator extends UnaryOperator
abstract class PostfixUnaryOperator extends UnaryOperator

case class AssignOperator() extends Operator
case class AddOperator() extends Operator
case class SubOperator() extends Operator
case class MulOperator() extends Operator
case class DivOperator() extends Operator

case class UnarySubOperator() extends PrefixUnaryOperator