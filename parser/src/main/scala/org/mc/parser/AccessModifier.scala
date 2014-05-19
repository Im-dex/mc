package org.mc.parser

object AccessModifier {
    def default() = new Public
}

sealed abstract class AccessModifier

final case class Public() extends AccessModifier
final case class Private() extends AccessModifier
