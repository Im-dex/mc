package org.mc.idea_plugin.psi.elements;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class ExpressionListElement extends ASTWrapperPsiElement {
    public ExpressionListElement(@NotNull ASTNode node) {
        super(node);
    }
}
