package org.mc.idea_plugin

import org.mc.parser._
import com.intellij.lang.{ASTNode, PsiBuilder}
import org.mc.idea_plugin.psi.McTypes
import com.intellij.psi.tree.IElementType

object AstBuilder {
    def build(ast: Ast, builder: PsiBuilder): ASTNode = {
        buildImpl(ast, builder)
        builder.getTreeBuilt
    }

    private def buildImpl(ast: Ast, builder: PsiBuilder): Unit = {
        ast match {
            case Ast.ExpressionList(expressions) =>
                val mark = builder.mark()
                expressions.foreach(buildImpl(_, builder))
                mark.done(McTypes.EXPRESSION_LIST)
            case expr: Ast.Expression =>
                buildExpression(expr, builder)
                skipToken(builder, McIdeaLexer.SEMICOLON)
        }
    }

    // skip current token if its type equals tokenType
    private def skipToken(builder: PsiBuilder, tokenType: IElementType): Unit = {
        if (builder.getTokenType == tokenType)
            builder.advanceLexer()
    }

    private def buildExpression(expression: Ast.Expression, builder: PsiBuilder): Unit = expression match {
        case Ast.ParenthesizedExpression(expr) =>
            val marker = builder.mark()
            skipToken(builder, McIdeaLexer.OPEN_PAREN)
            buildImpl(expr, builder)
            skipToken(builder, McIdeaLexer.CLOSE_PAREN)
            marker.done(McTypes.PARENTHESIZED_EXPR)
        case Ast.ErrorExpression(beforeTokenIndex) =>
            val marker = builder.mark()
            // TODO: builder.error()*/
            while (builder.rawTokenIndex() != beforeTokenIndex) {
                builder.advanceLexer()
            }
            marker.done(McTypes.ERROR_EXPRESSION)
        case expr: Ast.BinaryExpression =>
            val marker = builder.mark()
            buildImpl(expr.left, builder)
            builder.advanceLexer() // op token
            buildImpl(expr.right, builder)
            marker.done(getBinaryExpressionType(expr))
        case expr: Ast.UnaryExpression =>
            val marker = builder.mark()
            builder.advanceLexer() // op token
            buildImpl(expr.expression, builder)
            marker.done(getUnaryExpressionType(expr))
        case expr: Ast.EmptyExpression =>
            val marker = builder.mark()
            builder.advanceLexer()
            marker.done(McTypes.EMPTY_EXPR)
        case literal: Ast.Literal =>
            val marker = builder.mark()
            builder.advanceLexer()
            marker.done(getLiteralType(literal))
    }

    private def getBinaryExpressionType(expression: Ast.BinaryExpression): IElementType = expression match {
        case Ast.AddExpression(_,_) => McTypes.ADD_EXPR
        case Ast.SubExpression(_,_) => McTypes.SUB_EXPR
        case Ast.MulExpression(_,_) => McTypes.MUL_EXPR
        case Ast.DivExpression(_,_) => McTypes.DIV_EXPR
    }

    private def getUnaryExpressionType(expression: Ast.UnaryExpression): IElementType = expression match {
        case Ast.MinusExpression(expr) => McTypes.MINUS_UNARY_EXPR
        case Ast.PlusExpression(expr)  => McTypes.PLUS_UNARY_EXPR
    }

    private def getLiteralType(literal: Ast.Literal): IElementType = literal match {
        case Ast.IdLiteral()        => McTypes.ID_LITERAL
        case Ast.CharLiteral()      => McTypes.CHAR_LITERAL
        case Ast.StringLiteral()    => McTypes.STRING_LITERAL
        case Ast.DecNumberLiteral() => McTypes.DEC_NUMBER_LITERAL
        case Ast.HexNumberLiteral() => McTypes.HEX_NUMBER_LITERAL
        case Ast.BinNumberLiteral() => McTypes.BIN_NUMBER_LITERAL
        case Ast.FloatLiteral()     => McTypes.FLOAT_LITERAL
        case Ast.HexFloatLiteral()  => McTypes.HEX_FLOAT_LITERAL
        case Ast.DoubleLiteral()    => McTypes.DOUBLE_LITERAL
        case Ast.HexDoubleLiteral() => McTypes.HEX_DOUBLE_LITERAL
    }
}
