package errorfigure.module.modules.render;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.rendering.EventRender2D;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.api.value.Numbers;
import errorfigure.management.ModuleManager;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.ui.font.FontLoaders;
import errorfigure.utils.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class KeyBinds extends Module {
    public KeyBinds() {
        super("KeyBinds", ModuleType.Render);
    }

    Numbers<Double> x = new Numbers<>("PositionX", "PositionX", 200d, 0d, (double) RenderUtils.width(), 50d),
                    y = new Numbers<>("PositionY", "PositionY", 200d, 0d, (double) RenderUtils.height(), 50d);

    @EventTarget
    public void onRender(EventRender2D e) {
        int counter = 0;
        int counter1 = 0;
        for (Module module : ModuleManager.getModules()) {
            if (module.getKey() != 0) {
                counter++;
            }
        }
        Gui.drawRect(x.getValue() + 45, y.getValue() + 7, x.getValue() - 45, y.getValue() - 10, new Color(255, 255, 255, 170).getRGB());
        Gui.drawRect(x.getValue() + 45, y.getValue() + 7, x.getValue() - 45, y.getValue() + 7 + counter * 17, new Color(30, 30, 30, 100).getRGB());
        FontLoaders.kiona22.drawCenteredStringWithAdvancedShadow("KeyBinds", x.getValue().floatValue(), y.getValue().floatValue() - 5, 3, new Color(30, 30, 30).getRGB());
        for (Module module : ModuleManager.getModules()) {
            if (module.getKey() != 0) {
                String key = " [" + Keyboard.getKeyName(module.getKey()) + "]";
                FontLoaders.kiona18.drawCenteredStringWithAdvancedShadow(module.getName() + key, x.getValue().floatValue(),y.getValue().floatValue() + 13f + counter1 * 17f, 2, module.isEnabled() ? new Color(255, 255, 255).getRGB() : new Color(170, 170, 170).getRGB());
                counter1++;
            }
        }
        if (Mouse.getX() / 2 < x.getValue() + 45 && RenderUtils.height() - Mouse.getY() / 2 < y.getValue() + 7 && Mouse.getX() / 2 > x.getValue() - 45 && RenderUtils.height() - Mouse.getY() / 2 > y.getValue() - 10) {
            if (Mouse.isButtonDown(0)) {
                x.setValue(Mouse.getX() / 2d);
                y.setValue(RenderUtils.height() - Mouse.getY() / 2d);
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        setSuffix("ErrorFigure");
    }
}
