/*
 * Decompiled with CFR 0_132.
 */
package errorfigure.module.modules.world;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.world.EventTick;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import net.minecraft.item.ItemBlock;

import java.awt.*;

public class FastPlace
extends Module {
    public FastPlace() {
        super("FastPlace", ModuleType.World);
        this.setColor(new Color(226, 197, 78).getRGB());
    }

    @EventTarget
    private void onTick(EventTick e) {
        if (mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
            this.mc.rightClickDelayTimer = 0;
        }
    }
}

