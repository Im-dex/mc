package org.mc.idea_plugin.psi.elements;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * Created by dex on 06.06.2014.
 */
public class HexDoubleNumberElement extends ASTWrapperPsiElement {
    public HexDoubleNumberElement(@NotNull ASTNode node) {
        super(node);
    }
}
