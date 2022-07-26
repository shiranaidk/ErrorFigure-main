/*
 * Decompiled with CFR 0_132.
 */
package errorfigure.module.modules.player;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.world.EventPacketRecieve;
import errorfigure.api.value.Numbers;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

import java.awt.*;

public class AntiVelocity
extends Module {
    private Numbers<Float> percentageX = new Numbers<>("PercentageX", "percentageX", 0f, 0f, 100f, 5f);
    private Numbers<Float> percentageY = new Numbers<>("PercentageY", "percentageY", 0f, 0f, 100f, 5f);

    public AntiVelocity() {
        super("Velocity", ModuleType.Player);
        this.addValues(this.percentageX, percentageY);
        this.setColor(new Color(191, 191, 191).getRGB());
    }

    @EventTarget
    private void onPacket(EventPacketRecieve e) {
        if (e.getPacket() instanceof S12PacketEntityVelocity || e.getPacket() instanceof S27PacketExplosion) {
            e.setCancelled(true);
        }
    }
}

