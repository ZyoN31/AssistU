package com.assistuteam.assistu.view.themes;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

import com.formdev.flatlaf.FlatDarculaLaf;

/** @author assistu_team **/

public class AssistUDarkTheme extends FlatDarculaLaf {
    public static boolean setup() {
        return setup(new AssistUDarkTheme());
    }

    @Override
    public String getName() {
        return "AssistUDarkTheme";
    }

    // Y aqui, es donde registramos nuestras fuentes personalizadas :D
    public static void registerFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontFiles = {
            "/com/assistuteam/assistu/resources/fonts/Manjari-Regular.ttf",
            "/com/assistuteam/assistu/resources/fonts/readex-pro-latin-600-normal.tff",
            "/com/assistuteam/assistu/resources/fonts/Sansation-Regular.ttf"
        };
        for (String fontFile : fontFiles) {
            try (InputStream is = AssistUDarkTheme.class.getResourceAsStream(fontFile)) {
                if (is != null) {
                    Font font = Font.createFont(Font.TRUETYPE_FONT, is);
                    ge.registerFont(font);
                }
            } catch (Exception e) {
                // Puedes loggear el error si lo deseas
                System.err.println("ERROR, no se logro registrar la fuente: " + fontFile);
                e.printStackTrace();
            }
        }
    }
}
