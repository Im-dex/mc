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
                buildExpression(expr, builder)
                skipToken(builder, McIdeaLexer.SEMICOLON)
        }
    }

    // skip current token if its type equals tokenType
    private def skipToken(builder: PsiBuilder, tokenType: IElementType): Unit = {
        if (builder.getTokenType == tokenType)
            builder.advanceLexer()
    }

    private def buildExpression(expression: Expression, builder: PsiBuilder): Unit = expression match {
        case ParenthesizedExpression(expr) =>
            val marker = builder.mark()
            skipToken(builder, McIdeaLexer.OPEN_PAREN)
            buildImpl(expr, builder)
            skipToken(builder, McIdeaLexer.CLOSE_PAREN)
            marker.done(McTypes.PARENTHESIZED_EXPR)
        case ErrorExpression(beforeTokenIndex) =>
            val marker = builder.mark()
            // TODO: builder.error()*/
            while (builder.rawTokenIndex() != beforeTokenIndex) {
                builder.advanceLexer()
            }
            marker.done(McTypes.ERROR_EXPRESSION)
        case expr: BinaryExpression =>
            val marker = builder.mark()
            buildImpl(expr.left, builder)
            builder.advanceLexer() // op token
            buildImpl(expr.right, builder)
            marker.done(getBinaryExpressionType(expr))
        case expr: UnaryExpression =>
            val marker = builder.mark()
            builder.advanceLexer() // op token
            buildImpl(expr.expression, builder)
            marker.done(getUnaryExpressionType(expr))
        case expr: EmptyExpression =>
            if (builder.lookAhead(2) != null) {
                val marker = builder.mark()
                marker.done(McTypes.EMPTY_EXPR)
            } else {
                builder.advanceLexer()
            }
        case literal: Literal =>
            val marker = builder.mark()
            builder.advanceLexer()
            marker.done(getLiteralType(literal))
    }

    private def getBinaryExpressionType(expression: BinaryExpression): IElementType = expression match {
        case AddExpression(_,_) => McTypes.ADD_EXPR
        case SubExpression(_,_) => McTypes.SUB_EXPR
        case MulExpression(_,_) => McTypes.MUL_EXPR
        case DivExpression(_,_) => McTypes.DIV_EXPR
    }

    private def getUnaryExpressionType(expression: UnaryExpression): IElementType = expression match {
        case MinusExpression(expr) => McTypes.MINUS_UNARY_EXPR
    }

    private def getLiteralType(literal: Literal): IElementType = literal match {
        case IdLiteral()        => McTypes.ID_LITERAL
        case StringLiteral()    => McTypes.STRING_LITERAL
        case DecNumberLiteral() => McTypes.DEC_NUMBER_LITERAL
    }
}
