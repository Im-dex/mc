package org.mc.semantic

import org.mc.parser.{Block, ErrorExpression, Ast}
import org.mc.ir
import org.mc.semantic.error.{FatalParseException, ParseException}
import akka.actor.{FSM, Actor}
import scala.collection.mutable

class SemanticParser extends Actor with FSM[State, Ast] {

    startWith(Initial, Block(List.empty))

    when(Initial) {
        case Event(Block(_)) => goto(Scope)
    }

    onTransition {
        case Initial -> Block =>
    }

    private val scopeSymbols = mutable.Stack[SymbolTable]()
/*    @throws(classOf[ParseException])
    @throws(classOf[FatalParseException])
    def parse(ast: Ast): List[ir.Operation] = {
        ast match {
            case Block(blockAst) =>
                val result = List[ir.Operation]()
                blockAst.foldLeft(result)((outList, expressionAst) => outList ::: parse(expressionAst))

            case _: ErrorExpression => throw ParseException("AST contains errors")

            case
        }
    }*/
}
