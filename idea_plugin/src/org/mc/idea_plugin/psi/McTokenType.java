package org.mc.idea_plugin.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.mc.idea_plugin.McLanguage;

public class McTokenType extends IElementType {
    public McTokenType(@NotNull @NonNls String debugName) {
        super(debugName, McLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "McTokenType." + super.toString();
    }
}
