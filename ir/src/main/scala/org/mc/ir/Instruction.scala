package org.mc.ir

sealed abstract class Instruction

final case class DefVar(name: String, init: Operand) extends Instruction
final case class DefVal(name: String, init: Operand) extends Instruction

final case class Assign(out: Symbol, value: Operand) extends Instruction
final case class Add(lhs: Operand, rhs: Operand) extends Instruction
final case class Sub(lhs: Operand, rhs: Operand) extends Instruction
final case class Mul(lhs: Operand, rhs: Operand) extends Instruction
final case class Div(lhs: Operand, rhs: Operand) extends Instruction