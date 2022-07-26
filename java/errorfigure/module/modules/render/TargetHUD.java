package errorfigure.module.modules.render;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.rendering.EventRender2D;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.api.value.Numbers;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.module.modules.combat.Killaura;
import errorfigure.ui.font.FontLoaders;
import errorfigure.utils.TimerUtils;
import errorfigure.utils.render.RenderUtils;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class TargetHUD extends Module {
    public TargetHUD() {
        super("TargetHUD", ModuleType.Render);
        addValues(posX, posY);
    }
    TimerUtils timer = new TimerUtils();
    private final Numbers<Double> posX = new Numbers<>("PositionX", "PositionX", 600d, 0d, 1000d, 50d);
    private final Numbers<Double> posY = new Numbers<>("PositionY", "PositionY", 400d, 0d, 1000d, 50d);

    @EventTarget
    public void onRender2D(EventRender2D event) {
        if (Killaura.target != null) {
            if (Killaura.Mode.equals(Killaura.AuraMode.Single)) {
                draw(posY.getValue(), Killaura.target.getName(), Killaura.target.getHealth(), Killaura.target.getMaxHealth(), Killaura.target instanceof EntityPlayer ? Killaura.target.getTotalArmorValue() : 20, Killaura.target);
            }
            else {
                int i = 0;
                for (Entity entity : Killaura.targets) {
                    EntityLivingBase e = (EntityLivingBase) entity;
                    draw(posY.getValue() + i * 50, e.getName(), e.getHealth(), e.getMaxHealth(), e instanceof EntityPlayer ? Killaura.target.getTotalArmorValue() : 20, e);
                    i++;
                }
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        setSuffix("ErrorFigure");
    }

    public void draw(double y, String name, double health, double maxHealth, double armor, EntityLivingBase target) {
        Gui.drawRect(posX.getValue() - 42, y - 24, posX.getValue() + 74, y + 19, new Color(30, 30, 30, 35).getRGB());
        Gui.drawRect(posX.getValue() - 41.5, y - 23.5, posX.getValue() + 73.5, y + 18.5, new Color(30, 30, 30, 35).getRGB());
        Gui.drawRect(posX.getValue() - 41, y - 23, posX.getValue() + 73, y + 18, new Color(30, 30, 30, 35).getRGB());
        Gui.drawRect(posX.getValue() - 40.5, y - 22.5, posX.getValue() + 72.5, y + 17.5, new Color(30, 30, 30, 35).getRGB());
        Gui.drawRect(posX.getValue() - 40, y - 22, posX.getValue() + 72, y + 17, new Color(240, 240, 240).getRGB());
        FontLoaders.kiona24.drawStringWithAdvancedShadow(name, posX.getValue() - 1, y - 17, 3, new Color(30, 30, 30).getRGB());
        Gui.drawRect(posX.getValue(), y - 1, posX.getValue() + 68, y + 3, new Color(50, 50, 50).getRGB());
        Gui.drawRect(posX.getValue(), y - 1, posX.getValue() + 68 * (health / maxHealth) + 1.5, y + 3, new Color(180, 180, 180, 50).getRGB());
        Gui.drawRect(posX.getValue(), y - 1, posX.getValue() + 68 * (health / maxHealth) + 1, y + 3, new Color(180, 180, 180, 50).getRGB());
        Gui.drawRect(posX.getValue(), y - 1, posX.getValue() + 68 * (health / maxHealth) + 0.5, y + 3, new Color(180, 180, 180, 50).getRGB());
        Gui.drawRect(posX.getValue(), y - 1, posX.getValue() + 68 * (health / maxHealth), y + 3, new Color(251, 186, 221).getRGB());
        Gui.drawRect(posX.getValue(), y + 7, posX.getValue() + 68, y + 11, new Color(50, 50, 50).getRGB());
        Gui.drawRect(posX.getValue(), y + 7, posX.getValue() + 68 * (armor / 20) + 1.5, y + 11, new Color(180, 180, 180, 50).getRGB());
        Gui.drawRect(posX.getValue(), y + 7, posX.getValue() + 68 * (armor / 20) + 1, y + 11, new Color(180, 180, 180, 50).getRGB());
        Gui.drawRect(posX.getValue(), y + 7, posX.getValue() + 68 * (armor / 20) + 0.5, y + 11, new Color(180, 180, 180, 50).getRGB());
        Gui.drawRect(posX.getValue(), y + 7, posX.getValue() + 68 * (armor / 20), y + 11, new Color(142, 202, 234).getRGB());

        RenderUtils.drawHead((AbstractClientPlayer) target, (int) (posX.getValue() - 35), (int) (y - 18), 30, 30);
    }
}
