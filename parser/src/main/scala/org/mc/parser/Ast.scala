package org.mc.parser

import beaver.Symbol

sealed abstract class Ast extends Symbol with Immutable

object Ast {
    sealed abstract class Expression extends Ast with Immutable
    sealed abstract class Statement extends Ast with Immutable
    sealed abstract class Modifier extends Ast with Immutable

    //=========================================================================================================
    // module
    //=========================================================================================================

    final case class Module(statements: List[Statement]) extends Ast with Immutable

    //=========================================================================================================
    // expressions
    //=========================================================================================================

    sealed abstract class BinaryExpression extends Expression with Immutable {
        val left: Expression
        val right: Expression
    }

    sealed abstract class UnaryExpression extends Expression with Immutable {
        val expression: Expression
    }

    sealed abstract class Literal extends Expression with Immutable {
        val text: String
    }


    final case class ExpressionList(expressions: List[Expression]) extends Ast with Immutable
    final case class EmptyExpression() extends Expression with Immutable
    final case class ErrorExpression() extends Expression with Immutable
    final case class ParenthesizedExpression(expression: Expression) extends Expression with Immutable

    final case class AddExpression(left: Expression, right: Expression) extends BinaryExpression with Immutable
    final case class SubExpression(left: Expression, right: Expression) extends BinaryExpression with Immutable
    final case class MulExpression(left: Expression, right: Expression) extends BinaryExpression with Immutable
    final case class DivExpression(left: Expression, right: Expression) extends BinaryExpression with Immutable
    final case class MinusExpression(expression: Expression) extends UnaryExpression with Immutable
    final case class PlusExpression(expression: Expression) extends UnaryExpression with Immutable

    final case class IdLiteral(text: String) extends Literal with Immutable
    final case class DecNumberLiteral(text: String) extends Literal with Immutable
    final case class HexNumberLiteral(text: String) extends Literal with Immutable
    final case class BinNumberLiteral(text: String) extends Literal with Immutable
    final case class FloatLiteral(text: String) extends Literal with Immutable
    final case class HexFloatLiteral(text: String) extends Literal with Immutable
    final case class DoubleLiteral(text: String) extends Literal with Immutable
    final case class HexDoubleLiteral(text: String) extends Literal with Immutable
    final case class CharLiteral(text: String) extends Literal with Immutable
    final case class StringLiteral(text: String) extends Literal with Immutable

    //final case class ValueDefinition(token: IdToken, init: Ast) extends Ast
    //final case class VariableDefinition(token: IdToken, init: Ast) extends Ast

    //=========================================================================================================
    // statements
    //=========================================================================================================

    sealed abstract class TypeDeclaration extends Statement with Immutable

    final case class ErrorStatement() extends Statement with Immutable
    final case class ErrorTypeDeclaration() extends TypeDeclaration with Immutable

    final case class ModuleStatement() extends Statement with Immutable
    final case class ImportStatement() extends Statement with Immutable

    final case class ClassDeclaration(name: String, modifiers: List[Modifier], baseClass: Option[String],
                                      interfaces: List[String]) extends TypeDeclaration with Immutable

    //=========================================================================================================
    // modifiers
    //=========================================================================================================

    final case class Private() extends Modifier with Immutable
    final case class Protected() extends Modifier with Immutable
    final case class Final() extends Modifier with Immutable
}