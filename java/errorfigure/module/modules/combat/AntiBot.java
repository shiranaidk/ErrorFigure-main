/*
 * Decompiled with CFR 0_132.
 */
package errorfigure.module.modules.combat;

import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.utils.Helper;
import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class AntiBot
extends Module {
    public AntiBot() {
        super("AntiBot", ModuleType.Combat);
        this.setColor(new Color(217, 149, 251).getRGB());
    }

    public boolean isServerBot(Entity entity) {
        if (this.isEnabled()) {
            if (Helper.onServer("hypixel")) {
                if (entity.getDisplayName().getFormattedText().startsWith("\u00a7") && !entity.isInvisible() && !entity.getDisplayName().getFormattedText().toLowerCase().contains("npc")) {
                    return false;
                }
                return true;
            }
            if (Helper.onServer("mineplex")) {
                for (Object object : this.mc.theWorld.playerEntities) {
                    EntityPlayer entityPlayer = (EntityPlayer)object;
                    if (entityPlayer == null || entityPlayer == this.mc.thePlayer || !entityPlayer.getName().startsWith("Body #") && entityPlayer.getMaxHealth() != 20.0f) continue;
                    return true;
                }
            }
        }
        return false;
    }
}

