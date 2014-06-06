package org.mc.idea_plugin.highlighting

import com.intellij.psi.tree.IElementType
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.markup.TextAttributes
import java.awt.{Font, Color}
import org.mc.idea_plugin.McIdeaLexer
import com.intellij.psi.TokenType

object Highlighter {
    private val MULTI_LINE_COMMENT = createTextAttributesKey("MC_MULTI_LINE_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT)
    private val SINGLE_LINE_COMMENT = createTextAttributesKey("MC_SINGLE_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
    private val NUMBER = createTextAttributesKey("MC_NUMBER", DefaultLanguageHighlighterColors.NUMBER)
    private val OPERATOR = createTextAttributesKey("MC_OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
    private val SEMICOLON = createTextAttributesKey("MC_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON)
    private val ERROR = createTextAttributesKey("MC_ERROR", new TextAttributes(Color.RED, null, null, null, Font.BOLD))
    private val KEYWORD = createTextAttributesKey("MC_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)

    private val COMMENT_KEYS = Array(MULTI_LINE_COMMENT, SINGLE_LINE_COMMENT)
    private val NUMBER_KEYS = Array(NUMBER)
    private val OPERATOR_KEYS = Array(OPERATOR)
    private val SEMICOLON_KEYS = Array(SEMICOLON)
    private val ERROR_KEYS = Array(ERROR)
    private val KEYWORD_KEYS = Array(KEYWORD)
    private val EMPTY_KEYS: Array[TextAttributesKey] = Array()

    def getTokenHighlights(tokenType: IElementType): Array[TextAttributesKey] = {
        tokenType match {
            case McIdeaLexer.MULTI_LINE_COMMENT
                 | McIdeaLexer.SINGLE_LINE_COMMENT => COMMENT_KEYS
            case McIdeaLexer.NUMBER                => NUMBER_KEYS
            case McIdeaLexer.PLUS
                | McIdeaLexer.MINUS
                | McIdeaLexer.ASTERISK
                | McIdeaLexer.DIV                  => OPERATOR_KEYS
            case McIdeaLexer.KW_VAL
                | McIdeaLexer.KW_VAR
                | McIdeaLexer.KW_DEF
                | McIdeaLexer.KW_CLASS
                | McIdeaLexer.KW_INTERFACE
                | McIdeaLexer.KW_PUBLIC
                | McIdeaLexer.KW_PRIVATE
                | McIdeaLexer.KW_FINAL
                | McIdeaLexer.KW_EXTENDS
                | McIdeaLexer.KW_IMPLEMENTS
                | McIdeaLexer.KW_OVERRIDE
                | McIdeaLexer.KW_AS
                | McIdeaLexer.KW_IS
                | McIdeaLexer.KW_THIS
                | McIdeaLexer.KW_SUPER             => KEYWORD_KEYS
            case McIdeaLexer.SEMICOLON             => SEMICOLON_KEYS
            case TokenType.ERROR_ELEMENT           => ERROR_KEYS
            case _                                 => EMPTY_KEYS
        }
    }
}
