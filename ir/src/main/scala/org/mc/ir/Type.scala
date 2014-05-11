package org.mc.ir

sealed abstract class Type

final case class Char() extends Type
final case class Byte() extends Type
final case class UByte() extends Type
final case class Int16() extends Type
final case class UInt16() extends Type
final case class Int32() extends Type
final case class UInt32() extends Type
final case class Int64() extends Type
final case class UInt64() extends Type
final case class Float() extends Type
final case class Double() extends Type