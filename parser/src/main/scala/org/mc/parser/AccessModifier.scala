package org.mc.parser

sealed abstract class AccessModifier

final case class Public() extends AccessModifier
final case class Private() extends AccessModifier
