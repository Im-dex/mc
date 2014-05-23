package org.mc.idea_plugin.psi.elements;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class AddExpressionElement extends ASTWrapperPsiElement {
    public AddExpressionElement(@NotNull ASTNode node) {
        super(node);
    }
}
