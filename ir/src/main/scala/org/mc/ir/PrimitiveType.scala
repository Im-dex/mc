package org.mc.ir

sealed abstract class PrimitiveType extends Type

final case class Char() extends PrimitiveType
final case class Byte() extends PrimitiveType
final case class UByte() extends PrimitiveType
final case class Int16() extends PrimitiveType
final case class UInt16() extends PrimitiveType
final case class Int32() extends PrimitiveType
final case class UInt32() extends PrimitiveType
final case class Int64() extends PrimitiveType
final case class UInt64() extends PrimitiveType
final case class Float() extends PrimitiveType
final case class Double() extends PrimitiveType
final case class Pointer() extends PrimitiveType
