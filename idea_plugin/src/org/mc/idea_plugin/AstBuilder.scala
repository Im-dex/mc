package org.mc.idea_plugin

import org.mc.parser._
import com.intellij.lang.{ASTNode, PsiBuilder}
import org.mc.parser.BinaryExpression
import org.mc.parser.UnaryExpression
import org.mc.parser.ExpressionList
import org.mc.parser.Expression
import org.mc.idea_plugin.psi.McTypes
import com.intellij.psi.tree.IElementType

object AstBuilder {
    def build(ast: Ast, builder: PsiBuilder): ASTNode = {
        buildImpl(ast, builder)
        builder.getTreeBuilt
    }

    private def buildImpl(ast: Ast, builder: PsiBuilder): Unit = {
        ast match {
            case ExpressionList(expressions) =>
                val mark = builder.mark()
                expressions.foreach(buildImpl(_, builder))
                mark.done(McTypes.EXPRESSION_LIST)
            case expr: Expression =>
                val marker = builder.mark()
                val markerType = buildExpression(expr, builder)

                // fake empty expression before EOF
                if (markerType == McTypes.EMPTY_EXPR && builder.lookAhead(1) == null) {
                    marker.drop()
                } else {
                    marker.done(markerType)

                    // attach semicolon to expression
                    if (builder.getTokenType == McIdeaLexer.SEMICOLON) {
                        builder.advanceLexer()
                    }
                }
        }
    }

    private def buildExpression(expression: Expression, builder: PsiBuilder): IElementType = expression match {
        case expr: BinaryExpression =>
            buildImpl(expr.left, builder)
            builder.advanceLexer() // op token
            buildImpl(expr.right, builder)
            buildBinaryExpression(expr)
        case expr: UnaryExpression =>
            builder.advanceLexer() // op token
            buildImpl(expr.expression, builder)
            buildUnaryExpression(expr)
        case expr: EmptyExpression =>
            McTypes.EMPTY_EXPR
        case literal: Literal =>
            val result = buildLiteral(literal)
            builder.advanceLexer()
            result
    }

    private def buildBinaryExpression(expression: BinaryExpression): IElementType = expression match {
        case AddExpression(_,_) => McTypes.ADD_EXPR
        case SubExpression(_,_) => McTypes.SUB_EXPR
        case MulExpression(_,_) => McTypes.MUL_EXPR
        case DivExpression(_,_) => McTypes.DIV_EXPR
    }

    private def buildUnaryExpression(expression: UnaryExpression): IElementType = expression match {
        case MinusExpression(expr) => McTypes.MINUS_UNARY_EXPR
    }

    private def buildLiteral(literal: Literal): IElementType = literal match {
        case IdLiteral(token)        => McTypes.ID_LITERAL
        case StringLiteral(token)    => McTypes.STRING_LITERAL
        case DecNumberLiteral(token) => McTypes.DEC_NUMBER_LITERAL
    }
}
