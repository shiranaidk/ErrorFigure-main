/*
 * Decompiled with CFR 0_132.
 */
package errorfigure.module.modules.render;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.world.EventTick;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import java.awt.Color;

public class FullBright
extends Module {
    private float old;

    public FullBright() {
        super("FullBright", ModuleType.Render);
        this.setColor(new Color(244, 255, 149).getRGB());
    }

    @Override
    public void onEnable() {
        this.old = this.mc.gameSettings.gammaSetting;
    }

    @EventTarget
    private void onTick(EventTick e) {
        this.mc.gameSettings.gammaSetting = 1.5999999E7f;
    }

    @Override
    public void onDisable() {
        this.mc.gameSettings.gammaSetting = this.old;
    }
}

