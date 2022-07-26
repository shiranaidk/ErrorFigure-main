package errorfigure.module.modules.player;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.world.EventTick;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.api.value.Numbers;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.utils.Helper;
import errorfigure.utils.MoveUtils;
import errorfigure.utils.PlayerUtils;

public class GameSpeed extends Module {
    public GameSpeed() {
        super("GameSpeed", ModuleType.Player);
        addValues(speed);
    }

    Numbers<Double> speed = new Numbers<>("Speed", "Speed", 1d, 0.5, 3d, 0.1);
    public void onEnable() {
    }

    @EventTarget
    public void onUpdate(EventTick e) {
        mc.timer.timerSpeed = speed.getValue().floatValue();
    }

    public void onDisable() {
        mc.timer.timerSpeed = 1f;
    }
}
