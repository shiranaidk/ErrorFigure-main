/*
 * Decompiled with CFR 0_132.
 */
package errorfigure.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.api.value.Option;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;

import java.awt.*;

public class Sprint
extends Module {
    private Option<Boolean> omni = new Option<Boolean>("Omni-Directional", "omni", true);

    public Sprint() {
        super("Sprint", ModuleType.Movement);
        this.setColor(new Color(158, 205, 125).getRGB());
        this.addValues(this.omni);
    }

    @EventTarget
    private void onUpdate(EventUpdate event) {
        if (event.isPre() && this.mc.thePlayer.getFoodStats().getFoodLevel() > 6 && this.omni.getValue() != false ? this.mc.thePlayer.moving() : this.mc.thePlayer.moveForward > 0.0f && !mc.thePlayer.isCollidedHorizontally) {
            this.mc.thePlayer.setSprinting(true);
        }
    }
}

