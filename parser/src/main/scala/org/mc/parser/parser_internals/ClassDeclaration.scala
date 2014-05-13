package org.mc.parser.parser_internals

final case class ClassDeclaration(name: String, modifiers: ClassModifiers, inheritanceInfo: Option[ClassInheritanceInfo])
