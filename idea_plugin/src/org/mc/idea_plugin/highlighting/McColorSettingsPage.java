package org.mc.idea_plugin.highlighting;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mc.idea_plugin.McIcons;

import javax.swing.*;
import java.util.Map;

public class McColorSettingsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] DESCRIPTOR = new AttributesDescriptor[] {
            new AttributesDescriptor("Multi-line comment", Highlighter.MULTI_LINE_COMMENT()),
            new AttributesDescriptor("Single-line comment", Highlighter.SINGLE_LINE_COMMENT()),
            new AttributesDescriptor("Number", Highlighter.NUMBER()),
            new AttributesDescriptor("String", Highlighter.STRING()),
            new AttributesDescriptor("Operator", Highlighter.OPERATOR()),
            new AttributesDescriptor("Semicolon", Highlighter.SEMICOLON()),
            new AttributesDescriptor("Error", Highlighter.ERROR()),
            new AttributesDescriptor("Keyword", Highlighter.KEYWORD()),
    };

    @Nullable
    @Override
    public Icon getIcon() {
        return McIcons.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new McSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return "val a = 20 - \"string\" + 'd'";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return DESCRIPTOR;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "mc";
    }
}
