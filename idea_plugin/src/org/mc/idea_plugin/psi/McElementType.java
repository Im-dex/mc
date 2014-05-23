package org.mc.idea_plugin.psi;

import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.mc.idea_plugin.McLanguage;

public class McElementType extends IElementType {
    public McElementType(@NotNull @NonNls String debugName) {
        super(debugName, McLanguage.INSTANCE);
    }
}
