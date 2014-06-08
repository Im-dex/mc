package org.mc.idea_plugin;

import beaver.Parser;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.mc.parser.Ast;
import org.mc.parser.McParser;
import org.mc.parser.McScanner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

public class McIdeaParser implements PsiParser {
    @NotNull
    @Override
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        final CharSequence text = builder.getOriginalText();
        final String textData = String.valueOf(text);

        final InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(textData.getBytes()));
        final McScanner scanner = new McScanner(reader);

        final McIdeaParserListener listener = new McIdeaParserListener();
        final McParser parser = new McParser(listener);
        try {
            final Object astObject = parser.parse(scanner);
            final Ast ast = (Ast)astObject;
            return AstBuilder.build(ast, builder);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } catch (Parser.Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
