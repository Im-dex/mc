package org.mc.idea_plugin;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class McFile extends PsiFileBase {
    protected McFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, McLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return McFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "mc source file";
    }
}
