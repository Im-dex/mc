package org.mc.ir

sealed abstract class Operand[T <: Type]

final case class Symbol[T <: Type]() extends Operand[T]
final case class Constant[T <: PrimitiveType]() extends Operand[T]