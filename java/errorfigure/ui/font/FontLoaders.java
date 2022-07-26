/*
 * Decompiled with CFR 0_132.
 */
package errorfigure.ui.font;

import java.awt.Font;
import java.io.InputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public abstract class FontLoaders {
    public static CFontRenderer kiona14 = new CFontRenderer(FontLoaders.getKiona(14), true, true);

    public static CFontRenderer kiona16 = new CFontRenderer(FontLoaders.getKiona(16), true, true);
    public static CFontRenderer kiona18 = new CFontRenderer(FontLoaders.getKiona(18), true, true);
    public static CFontRenderer kiona20 = new CFontRenderer(FontLoaders.getKiona(20), true, true);
    public static CFontRenderer kiona22 = new CFontRenderer(FontLoaders.getKiona(22), true, true);
    public static CFontRenderer kiona24 = new CFontRenderer(FontLoaders.getKiona(24), true, true);
    public static CFontRenderer kiona26 = new CFontRenderer(FontLoaders.getKiona(26), true, true);
    public static CFontRenderer kiona28 = new CFontRenderer(FontLoaders.getKiona(28), true, true);
    public static CFontRenderer logo50 = new CFontRenderer(FontLoaders.getKiona(50), true, true);

    public static Font getKiona(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("ETB/ETB.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    private static Font getLogo(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("ETB/Candaral.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }
}

