/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package errorfigure.module.modules.render;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.rendering.EventPostRenderPlayer;
import errorfigure.api.events.rendering.EventPreRenderPlayer;
import errorfigure.api.value.Mode;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Chams
extends Module {
    public Mode<Enum> mode = new Mode("Mode", "mode", (Enum[])ChamsMode.values(), (Enum)ChamsMode.Textured);

    public Chams() {
        super("Chams", ModuleType.Render);
        this.addValues(this.mode);
        this.setColor(new Color(159, 190, 192).getRGB());
    }

    @EventTarget
    private void preRenderPlayer(EventPreRenderPlayer e) {
        GL11.glEnable((int)32823);
        GL11.glPolygonOffset((float)1.0f, (float)-1100000.0f);
    }

    @EventTarget
    private void postRenderPlayer(EventPostRenderPlayer e) {
        GL11.glDisable((int)32823);
        GL11.glPolygonOffset((float)1.0f, (float)1100000.0f);
    }

    public static enum ChamsMode {
        Textured,
        Normal;
    }

}

