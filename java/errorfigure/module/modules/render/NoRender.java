/*
 * Decompiled with CFR 0_132.
 */
package errorfigure.module.modules.render;

import errorfigure.module.Module;
import errorfigure.module.ModuleType;

import java.awt.Color;

public class NoRender
extends Module {
    public NoRender() {
        super("NoRender", ModuleType.Render);
        this.setColor(new Color(166, 185, 123).getRGB());
    }
}

