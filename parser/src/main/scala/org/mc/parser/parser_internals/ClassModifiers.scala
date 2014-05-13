package org.mc.parser.parser_internals

import org.mc.parser.{Private, AccessModifier}

object ClassModifiers {
  def default() = {
    new ClassModifiers(new Private(), false)
  }
}

final case class ClassModifiers(accessModifier: AccessModifier, finalFlag: Boolean)
