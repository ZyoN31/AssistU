package com.assistuteam.assistu.view.themes;

import com.formdev.flatlaf.FlatDarculaLaf;

/** @author assistu_team **/

public class AssistUDarkTheme extends FlatDarculaLaf {
    public static boolean setup() {
        return setup( new AssistUDarkTheme());
    }

    @Override
    public String getName() {
        return "AssistUDarkTheme";
    }
}
