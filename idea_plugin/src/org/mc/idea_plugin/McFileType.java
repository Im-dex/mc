package org.mc.idea_plugin;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class McFileType extends LanguageFileType {
    public static final McFileType INSTANCE = new McFileType();

    /**
     * Creates a language file type for the specified language.
     */
    protected McFileType() {
        super(McLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "mc language";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "mc language source file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "mc";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return McIcons.FILE;
    }
}
