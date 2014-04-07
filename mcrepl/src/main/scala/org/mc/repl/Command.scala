package org.mc.repl

abstract class Command extends Immutable

final case class Help() extends Command
final case class Quit() extends Command
final case class ExecCode(source: String) extends Command
