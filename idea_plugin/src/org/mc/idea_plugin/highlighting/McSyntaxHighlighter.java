package org.mc.idea_plugin.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.mc.idea_plugin.McIdeaLexer;
import org.mc.lexer.McLexer;

import java.io.Reader;

public class McSyntaxHighlighter extends SyntaxHighlighterBase {
    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new McIdeaLexer(new McLexer((Reader)null));
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return Highlighter.getTokenHighlights(tokenType);
    }
}