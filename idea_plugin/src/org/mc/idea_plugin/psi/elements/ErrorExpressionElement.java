package org.mc.idea_plugin.psi.elements;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class ErrorExpressionElement extends ASTWrapperPsiElement {
    public ErrorExpressionElement(@NotNull ASTNode node) {
        super(node);
    }
}
