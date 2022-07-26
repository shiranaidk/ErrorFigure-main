/*
 * Decompiled with CFR 0_132.
 */
package errorfigure.module.modules.render;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.Client;
import errorfigure.api.events.rendering.EventRenderCape;
import errorfigure.management.FriendManager;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;

import java.awt.*;

public class Capes
extends Module {
    public Capes() {
        super("Capes", ModuleType.Render);
        this.setColor(new Color(159, 190, 192).getRGB());
        this.setEnabled(true);
        this.setRemoved(true);
    }

    @EventTarget
    public void onRender(EventRenderCape event) {
        if (this.mc.theWorld != null && FriendManager.isFriend(event.getPlayer().getName())) {
            event.setLocation(Client.CLIENT_CAPE);
            event.setCancelled(true);
        }
    }
}

