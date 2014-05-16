package org.mc.repl

object Main extends App {

    private val executor = CommandExecutor()

    override def main(args: Array[String]) {
        super.main(args)
        doIteration()
    }

    private def doIteration() {
        print("mc REPL: >")
        val line = Console.readLine()
        execLine(line)
        doIteration()
    }

    private def execLine(line: String) {
        val command = parseCommand(line)
        executor.execCommand(command)
    }

    private def parseCommand(line: String): Command = {
        line.toLowerCase match {
            case "quit" => Quit()
            case "help" => Help()
            case _      => ExecCode(line)
        }
    }
}