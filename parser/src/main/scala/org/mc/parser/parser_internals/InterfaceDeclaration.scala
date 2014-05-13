package org.mc.parser.parser_internals

import org.mc.parser.AccessModifier

final case class InterfaceDeclaration(name: String, accessModifier: AccessModifier, baseInterfaces: List[String])
