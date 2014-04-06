package org.mc.parser

abstract class Operator

case class UnaryOperator(isPrefix: Boolean) extends Operator

case class AssignOperator() extends Operator
case class AddOperator() extends UnaryOperator
case class SubOperator() extends UnaryOperator
case class MulOperator() extends Operator
case class DivOperator() extends Operator