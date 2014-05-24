package org.mc.idea_plugin

import org.mc.parser._
import com.intellij.lang.{ASTNode, PsiBuilder}
import org.mc.parser.BinaryExpression
import org.mc.parser.UnaryExpression
import org.mc.parser.ExpressionList
import org.mc.idea_plugin.psi.McTypes

object AstBuilder {
    def build(ast: Ast, builder: PsiBuilder): ASTNode = {
        buildImpl(ast, builder)
        builder.getTreeBuilt
    }

    private def buildImpl(ast: Ast, builder: PsiBuilder): Unit = {
        ast match {
            case ExpressionList(expressions) =>
                builder.advanceLexer()
                val mark = builder.mark()
                expressions.foreach(buildImpl(_, builder))
                mark.done(McTypes.EXPRESSION_LIST)
            case expr: BinaryExpression =>
                val mark = builder.mark()
                buildImpl(expr.left, builder)
                builder.advanceLexer() // op token
                buildImpl(expr.right, builder)
                doneBinaryExpression(expr, mark)
            case expr: UnaryExpression =>
                val mark = builder.mark()
                builder.advanceLexer() // op token
                buildImpl(expr.expression, builder)
                doneUnaryExpression(expr, mark)
            case expr: EmptyExpression =>
                processEmptyExpression(builder)
            case literal: Literal =>
                val mark = builder.mark()
                doneLiteral(literal, mark)
                builder.advanceLexer()
        }
    }

    private def doneBinaryExpression(expression: BinaryExpression, mark: PsiBuilder.Marker): Unit = expression match {
        case AddExpression(_,_) => mark.done(McTypes.ADD_EXPR)
        case SubExpression(_,_) => mark.done(McTypes.SUB_EXPR)
        case MulExpression(_,_) => mark.done(McTypes.MUL_EXPR)
        case DivExpression(_,_) => mark.done(McTypes.DIV_EXPR)
    }

    private def doneUnaryExpression(expression: UnaryExpression, mark: PsiBuilder.Marker): Unit = expression match {
        case MinusExpression(expr) => mark.done(McTypes.MINUS_UNARY_EXPR)
    }

    private def processEmptyExpression(builder: PsiBuilder): Unit = builder.lookAhead(1) match {
        case null => // eof
        case _    =>
            val mark = builder.mark()
            builder.advanceLexer()
            mark.done(McTypes.EMPTY_EXPR)
    }

    private def doneLiteral(literal: Literal, mark: PsiBuilder.Marker): Unit = literal match {
        case IdLiteral(token) => mark.done(McTypes.ID_LITERAL)
        case StringLiteral(token) => mark.done(McTypes.STRING_LITERAL)
        case DecNumberLiteral(token) => mark.done(McTypes.DEC_NUMBER_LITERAL)
    }
}
