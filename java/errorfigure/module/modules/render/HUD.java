/*
 * Decompiled with CFR 0_132.
 */
package errorfigure.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.Client;
import errorfigure.api.events.rendering.EventRender2D;
import errorfigure.api.value.Option;
import errorfigure.management.ModuleManager;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.ui.font.CFontRenderer;
import errorfigure.ui.font.FontLoaders;
import errorfigure.utils.PlayerUtils;
import errorfigure.utils.render.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class HUD
        extends Module {
    private Option<Boolean> info = new Option<Boolean>("Information", "information", true);
    private Option<Boolean> rainbow = new Option<Boolean>("Rainbow", "rainbow", false);
    public static boolean shouldMove;
    public HUD() {
        super("HUD", ModuleType.Render);
        this.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
        this.setEnabled(true);
        this.addValues(this.info, this.rainbow);
    }

    @EventTarget
    private void renderHud(EventRender2D event) {
        CFontRenderer font = FontLoaders.kiona16;
        if (!this.mc.gameSettings.showDebugInfo) {
            String name;
            String suffix;
            String direction;
            final CFontRenderer cFontRenderer3 = FontLoaders.kiona24;
            Client.instance.getClass();
//            Gui.drawRect(7f, 7f, 9f + cFontRenderer3.getStringWidth("Errorfigure.xyz | Release 220710 | Cracked By unrealizable#7226"), 8f, new Color(255, 255, 255).getRGB());
//            Gui.drawRect(7f, 8f, 9f + cFontRenderer3.getStringWidth("Errorfigure.xyz | Release 220710 | Cracked By unrealizable#7226"), 11f + cFontRenderer3.getStringHeight("FUCKYOUXIJINPING"), new Color(30, 30, 30).getRGB());
//            cFontRenderer3.drawStringWithAdvancedShadow("Errorfigure.xyz | Release 220710 | Cracked By unrealizable#7226", 8f, 10f, 2, new Color(255, 255, 255).getRGB());
            cFontRenderer3.drawStringWithAdvancedShadow("Errorfigure", 16f, 14f, 4, new Color(255, 255, 255).getRGB());
            FontLoaders.kiona18.drawStringWithAdvancedShadow("User: " + Client.userName, RenderUtils.width() - FontLoaders.kiona20.getStringWidth("User: " + Client.userName) - 2, RenderUtils.height() - FontLoaders.kiona20.getStringHeight("User: " + Client.userName) - 2, 3, new Color(255, 255, 255).getRGB());
            FontLoaders.kiona16.drawStringWithAdvancedShadow("BPS: " + String.format("%.3f", PlayerUtils.getBPS()), 2, RenderUtils.height() - FontLoaders.kiona20.getStringHeight("LARISSANMSL") - 2, 3, new Color(255, 255, 255).getRGB());
            FontLoaders.kiona16.drawStringWithAdvancedShadow("FPS: " + mc.getDebugFPS(), 2, RenderUtils.height() - FontLoaders.kiona20.getStringHeight("LARISSANMSL") * 2 - 6, 3, new Color(255, 255, 255).getRGB());

            ArrayList<Module> sorted = new ArrayList<Module>();
            Client.instance.getModuleManager();
            for (Module m : ModuleManager.getModules()) {
                if (!m.isEnabled() || m.wasRemoved()) continue;
                sorted.add(m);
            }
            sorted.sort((o1, o2) -> font.getStringWidth(o2.getName()) - font.getStringWidth(o1.getName()));
            int y = 1;
            int rainbowTick = 0;
            for (Module m : sorted) {
                name = m.getName();
                suffix = !m.getSuffix().isEmpty() ? " -> " + m.getSuffix() : "";
                float x1 = RenderUtils.width() - font.getStringWidth(name);
                float x2 = RenderUtils.width() - font.getStringWidth(suffix);
                Color rainbow = new Color(Color.HSBtoRGB((float)((double)this.mc.thePlayer.ticksExisted / 50.0 + Math.sin((double)rainbowTick / 50.0 * 1.6)) % 1.0f, 0.68f, 1.0f));
                font.drawStringWithAdvancedShadow(name, x1 - 8.0f, y + 8, 3, this.rainbow.getValue() != false ? rainbow.getRGB() : new Color(255, 255, 255).getRGB());
                if (++rainbowTick > 50) {
                    rainbowTick = 0;
                }
                y += 12;
            }
            if (this.info.getValue().booleanValue()) {
                this.drawPotionStatus(new ScaledResolution(this.mc));
            }
        }
    }

    private void drawPotionStatus(ScaledResolution sr) {
        CFontRenderer font = FontLoaders.kiona18;
        int y = -10;
        for (PotionEffect effect : this.mc.thePlayer.getActivePotionEffects()) {
            int ychat;
            Potion potion = Potion.potionTypes[effect.getPotionID()];
            String PType = I18n.format(potion.getName(), new Object[0]);
            switch (effect.getAmplifier()) {
                case 1: {
                    PType = String.valueOf(PType) + " II";
                    break;
                }
                case 2: {
                    PType = String.valueOf(PType) + " III";
                    break;
                }
                case 3: {
                    PType = String.valueOf(PType) + " IV";
                    break;
                }
            }
            if (effect.getDuration() < 600 && effect.getDuration() > 300) {
                PType = String.valueOf(PType) + "\u00a77:\u00a76 " + Potion.getDurationString(effect);
            } else if (effect.getDuration() < 300) {
                PType = String.valueOf(PType) + "\u00a77:\u00a7c " + Potion.getDurationString(effect);
            } else if (effect.getDuration() > 600) {
                PType = String.valueOf(PType) + "\u00a77:\u00a77 " + Potion.getDurationString(effect);
            }
            int n = ychat = this.mc.ingameGUI.getChatGUI().getChatOpen() ? 5 : -10;
            font.drawStringWithShadow(PType, sr.getScaledWidth() - font.getStringWidth(PType) - 2, sr.getScaledHeight() - font.getHeight() + y - 12 - ychat, potion.getLiquidColor());
            y -= 10;
        }
    }
}

