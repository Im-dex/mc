package org.mc.ir

sealed abstract class Operand(valueType: Type)

final case class Symbol(valueType: Type) extends Operand(valueType)
final case class Constant(valueType: Type) extends Operand(valueType)