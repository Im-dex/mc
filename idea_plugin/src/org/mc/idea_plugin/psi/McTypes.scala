package org.mc.idea_plugin.psi

import com.intellij.psi.tree.IElementType
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import org.mc.idea_plugin.psi.elements._

object McTypes {
    val ID_LITERAL: IElementType = new McElementType("ID")
    val STRING_LITERAL: IElementType = new McElementType("STRING")
    val DEC_NUMBER_LITERAL: IElementType = new McElementType("DEC_NUMBER")

    val EXPRESSION_LIST: IElementType = new McElementType("EXPRESSION_LIST")
    val EMPTY_EXPR: IElementType = new McElementType("EMPTY_EXPR")
    val MINUS_UNARY_EXPR: IElementType = new McElementType("MINUS_UNARY_EXPR")
    val ADD_EXPR: IElementType = new McElementType("ADD_EXPR")
    val SUB_EXPR: IElementType = new McElementType("SUB_EXPR")
    val MUL_EXPR: IElementType = new McElementType("MUL_EXPR")
    val DIV_EXPR: IElementType = new McElementType("DIV_EXPR")

    def createPsiElement(node: ASTNode): PsiElement = {
        node.getElementType match {
            case ID_LITERAL         => new IdElement(node)
            case STRING_LITERAL     => new StringElement(node)
            case DEC_NUMBER_LITERAL => new DecNumberElement(node)
            case EXPRESSION_LIST    => new ExpressionListElement(node)
            case EMPTY_EXPR         => new EmptyExpressionElement(node)
            case MINUS_UNARY_EXPR   => new MinusUnaryExpressionElement(node)
            case ADD_EXPR           => new AddExpressionElement(node)
            case SUB_EXPR           => new SubExpressionElement(node)
            case MUL_EXPR           => new MulExpressionElement(node)
            case DIV_EXPR           => new DivExpressionElement(node)
            case _                  => throw new AssertionError("Unknown element type: " + node.getElementType)
        }
    }
}
