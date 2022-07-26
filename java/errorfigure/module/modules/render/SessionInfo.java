package errorfigure.module.modules.render;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.rendering.EventRender2D;
import errorfigure.api.events.world.EventTick;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.api.events.world.EventWorldLoad;
import errorfigure.api.value.Numbers;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.module.modules.combat.Killaura;
import errorfigure.module.modules.world.Disabler;
import errorfigure.ui.font.FontLoaders;
import errorfigure.utils.PlayerUtils;
import errorfigure.utils.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class SessionInfo extends Module {
    public SessionInfo() {
        super("SessionInfo", ModuleType.Render);
    }

    int tick, sec, min, hour, world;
    public static int kills;
    String time, game, info, coor;
    Numbers<Double> x = new Numbers<>("posx", "posx", 100d, 0d, (double) RenderUtils.width(), 50d),
                    y = new Numbers<>("posy", "posy", 50d, 0d, (double) RenderUtils.width(), 50d);

    @EventTarget
    public void onWorldLoad(EventWorldLoad e ) {
        if (!Disabler.isHypixelLobby() && !mc.isSingleplayer()) {
            world++;
        }
    }
    @EventTarget
    public void onTick(EventTick e) {
        tick++;
        if (tick == 20) {
            tick = 0;
            sec++;
            if (sec == 60) {
                sec = 0;
                min++;
                if (min == 60) {
                    min = 0;
                    hour++;
                }
            }
        }
        time = "PlayingTime: " + sec + "s " + min + "m " + hour +"h";
        game = "Game: " + kills + "kills " + world + "games " + mc.thePlayer.getName();
        info = "Info: " + mc.debugFPS + "FPS " + String.format("%.2f", PlayerUtils.getBPS()) + "BPS " + mc.timer.timerSpeed + "timer";
        coor = "Coordinate: X:" + String.format("%.0f", mc.thePlayer.posX) + " Y:" + String.format("%.0f", mc.thePlayer.posY) + " Z:" + String.format("%.0f", mc.thePlayer.posZ);

    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        setSuffix("ErrorFigure");
        if (Killaura.target.isDead) {
            kills++;
        }
    }

    @EventTarget
    public void onRender(EventRender2D e) {
        Gui.drawRect(x.getValue() - 80, y.getValue() + 5, x.getValue() + 80, y.getValue() - 10, new Color(255, 255, 255, 170).getRGB());
        Gui.drawRect(x.getValue() - 80, y.getValue() + 5, x.getValue() + 80, y.getValue() + 60, new Color(30, 30, 30, 100).getRGB());
        FontLoaders.kiona22.drawCenteredStringWithAdvancedShadow("SessionInfo", x.getValue().floatValue(), y.getValue().floatValue() - 6f, 3, new Color(30, 30, 30).getRGB());
        FontLoaders.kiona20.drawStringWithAdvancedShadow(time, x.getValue() - 75f, y.getValue() + 11f, 2, new Color(255, 255, 255).getRGB());
        FontLoaders.kiona20.drawStringWithAdvancedShadow(game, x.getValue() - 75f, y.getValue() + 23f, 2, new Color(255, 255, 255).getRGB());
        FontLoaders.kiona20.drawStringWithAdvancedShadow(info, x.getValue() - 75f, y.getValue() + 35f, 2, new Color(255, 255, 255).getRGB());
        FontLoaders.kiona20.drawStringWithAdvancedShadow(coor, x.getValue() - 75f, y.getValue() + 47f, 2, new Color(255, 255, 255).getRGB());

        if (Mouse.getX() / 2 > x.getValue() - 80 && RenderUtils.height() - Mouse.getY() / 2 > y.getValue() - 10 && Mouse.getX() / 2 < x.getValue() + 80 && RenderUtils.height() - Mouse.getY() / 2 < y.getValue() + 5) {
            if (Mouse.isButtonDown(0)) {
                x.setValue((double) (Mouse.getX() / 2));
                y.setValue((double) (RenderUtils.height() - Mouse.getY() / 2));
            }
        }
    }
}
