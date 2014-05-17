package org.mc.idea_plugin;

import com.intellij.lang.Language;

public class McLanguage extends Language {
    public static final McLanguage INSTANCE = new McLanguage();

    protected McLanguage() {
        super("Mc");
    }
}
