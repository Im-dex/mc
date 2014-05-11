package org.mc.semantic


sealed trait State
case object Initial extends State
case object Scope extends State
