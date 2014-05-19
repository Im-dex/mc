package org.mc.parser

object ClassModifiers {
    def default() = new ClassModifiers(AccessModifier.default(), false)
}

final case class ClassModifiers(accessModifier: AccessModifier, finalFlag: Boolean)
