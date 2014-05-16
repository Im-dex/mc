package org.mc.repl

import org.mc.parser._
import java.io.{InputStreamReader, ByteArrayInputStream}

object CommandExecutor {
    def apply() = {
        new CommandExecutor()
    }
}

final class CommandExecutor {

    def execCommand(command: Command) {
        command match {
            case _: Help        => showHelp()
            case _: Quit        => sys.exit()
            case code: ExecCode => execCode(code.source)
        }
    }

    private def showHelp() {
        println("Help")
    }

    private def execCode(code: String) {
        val stream = new ByteArrayInputStream(code.getBytes("UTF-8"))
        val scanner = McScanner(new InputStreamReader(stream))
        val parser = new Parser(scanner)

        val ast = parser.parse()
        printData(ast.value.asInstanceOf[Ast], 0)
    }

    private def printData(ast: Ast, depth: Int) {
        print("  " * depth)

        ast match {
            case ExpressionsBlock(expressions) =>
                expressions.foreach(expression => printDataLn(expression, depth))

            case BinaryExpression(left, right, operator) =>
                printOperator(operator)
                printDataLn(left, depth + 1)
                printDataLn(right, depth + 1)

            case UnaryExpression(operand, None) =>
                printData(operand, depth)

            case UnaryExpression(operand, Some(operator)) =>
                printOperator(operator)
                printDataLn(operand, depth + 1)

            case _: ErrorExpression  =>
                println("Error")

            case IdConstant(token) =>
                println(token.name)

            case DecConstant(token) =>
                println(token.value)

            case StringConstant(token) =>
                println(token.value)

            case ValueDefinition(token, init) =>
                println(s"New value '${token.name}' = ")
                printDataLn(init, depth)

            case VariableDefinition(token, init) =>
                println(s"New variable '${token.name}'= ")
                printDataLn(init, depth)
        }
    }

    private def printDataLn(ast: Ast, depth: Int) {
        printData(ast, depth)
        println()
    }

    private def printOperator(operator: Operator) {
        val res = operator match {
            case _: AssignOperator   => "="
            case _: AddOperator      => "+"
            case _: SubOperator      => "-"
            case _: MulOperator      => "*"
            case _: DivOperator      => "/"
            case _: UnarySubOperator => "-"
        }

        print(res)
    }
}
