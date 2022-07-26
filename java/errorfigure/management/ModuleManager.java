/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package errorfigure.management;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.misc.EventKey;
import errorfigure.api.events.rendering.EventRender2D;
import errorfigure.api.events.rendering.EventRender3D;
import errorfigure.api.value.Mode;
import errorfigure.api.value.Numbers;
import errorfigure.api.value.Option;
import errorfigure.api.value.Value;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.module.modules.movement.*;
import errorfigure.utils.render.gl.GLUtils;
import errorfigure.module.modules.combat.*;
import errorfigure.module.modules.player.*;
import errorfigure.module.modules.render.*;
import errorfigure.module.modules.world.*;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager
implements Manager {
    public static List<Module> modules = new ArrayList<Module>();
    private boolean enabledNeededMod = true;
    public boolean nicetry = true;

    @Override
    public void init() {
        modules.add(new JumpReset());
        modules.add(new TargetStrafe());
        modules.add(new Notification());
        modules.add(new GameSpeed());
        modules.add(new Animations());
        modules.add(new Disabler());
        modules.add(new ClickGUI());
        modules.add(new HUD());
        modules.add(new Sprint());
        modules.add(new AutoClicker());
        modules.add(new Killaura());
        modules.add(new Reach());
        modules.add(new HitBox());
        modules.add(new AntiVelocity());
        modules.add(new Criticals());
        modules.add(new Speed());
        modules.add(new Longjump());
        modules.add(new Flight());
        modules.add(new NoFall());
        modules.add(new NoSlow());
        modules.add(new AntiBot());
        modules.add(new Nametags());
        modules.add(new Tracers());
        modules.add(new ESP());
        modules.add(new FastPlace());
        modules.add(new NoRender());
        modules.add(new FullBright());
        modules.add(new ChestStealer());
        modules.add(new AntiVoid());
        modules.add(new AutoHeal());
        modules.add(new Scaffold());
        modules.add(new SafeWalk());
        modules.add(new KeyBinds());
        modules.add(new Jesus());
        modules.add(new Phase());
        modules.add(new Chams());
        modules.add(new SessionInfo());

        modules.add(new Blink());
        modules.add(new TargetHUD());
        modules.add(new FastUse());
        modules.add(new PingSpoof());
        modules.add(new BowAimBot());
        modules.add(new Xray());
        modules.add(new ChestESP());
        modules.add(new InvManager());
        modules.add(new Step());

        modules.add(new Teleport());
        modules.add(new AutoSword());

        modules.add(new TPAura());

        
        modules.add(new InvMove());
        modules.add(new Teams());
        this.readSettings();
        for (Module m : modules) {
            m.makeCommand();
        }
        EventManager.register(this);
    }

    public static List<Module> getModules() {
        return modules;
    }

    public static Module getModuleByClass(Class<? extends Module> cls) {
        for (Module m : modules) {
            if (m.getClass() != cls) continue;
            return m;
        }
        return null;
    }

    public static Module getModuleByName(String name) {
        for (Module m : modules) {
            if (!m.getName().equalsIgnoreCase(name)) continue;
            return m;
        }
        return null;
    }

    public List<Module> getModulesInType(ModuleType t) {
        ArrayList<Module> output = new ArrayList<Module>();
        for (Module m : modules) {
            if (m.getType() != t) continue;
            output.add(m);
        }
        return output;
    }

    @EventTarget
    private void onKeyPress(EventKey e) {
        for (Module m : modules) {
            if (m.getKey() != e.getKey()) continue;
            m.setEnabled(!m.isEnabled());
        }
    }

    @EventTarget
    private void onGLHack(EventRender3D e) {
        GlStateManager.getFloat(2982, (FloatBuffer) GLUtils.MODELVIEW.clear());
        GlStateManager.getFloat(2983, (FloatBuffer)GLUtils.PROJECTION.clear());
        GlStateManager.glGetInteger(2978, (IntBuffer)GLUtils.VIEWPORT.clear());
    }

    @EventTarget
    private void on2DRender(EventRender2D e) {
        if (this.enabledNeededMod) {
            this.enabledNeededMod = false;
            for (Module m : modules) {
                if (!m.enabledOnStartup) continue;
                m.setEnabled(true);
            }
        }
    }

    private void readSettings() {
        List<String> binds = FileManager.read("Binds.txt");
        for (String v : binds) {
            String name = v.split(":")[0];
            String bind = v.split(":")[1];
            Module m = ModuleManager.getModuleByName(name);
            if (m == null) continue;
            m.setKey(Keyboard.getKeyIndex((String)bind.toUpperCase()));
        }
        List<String> enabled = FileManager.read("Enabled.txt");
        for (String v : enabled) {
            Module m = ModuleManager.getModuleByName(v);
            if (m == null) continue;
            m.enabledOnStartup = true;
        }
        List<String> vals = FileManager.read("Values.txt");
        for (String v : vals) {
            String name = v.split(":")[0];
            String values = v.split(":")[1];
            Module m = ModuleManager.getModuleByName(name);
            if (m == null) continue;
            for (Value value : m.getValues()) {
                if (!value.getName().equalsIgnoreCase(values)) continue;
                if (value instanceof Option) {
                    value.setValue(Boolean.parseBoolean(v.split(":")[2]));
                    continue;
                }
                if (value instanceof Numbers) {
                    value.setValue(Double.parseDouble(v.split(":")[2]));
                    continue;
                }
                ((Mode)value).setMode(v.split(":")[2]);
            }
        }
    }
}

