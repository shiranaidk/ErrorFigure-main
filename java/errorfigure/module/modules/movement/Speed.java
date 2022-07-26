package errorfigure.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.api.value.Mode;
import errorfigure.api.value.Numbers;
import errorfigure.api.value.Option;
import errorfigure.management.ModuleManager;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.module.modules.combat.Killaura;
import errorfigure.module.modules.player.GameSpeed;
import errorfigure.utils.MoveUtils;
import errorfigure.utils.PlayerUtils;
import errorfigure.utils.TimerUtils;

public class Speed extends Module {
    public Mode<Enum> mode = new Mode<Enum>("Mode", "Mode", SMode.values(), SMode.Hypixel);
    public Numbers<Double> timerSpeed = new Numbers("Timer", "Timer", 1.0, 0.1, 1.3, 0.05);
    public Option<Boolean> noAtkTimer = new Option<Boolean>("NoAttackTimer", "NoAttackTimer", true);
    TimerUtils timer = new TimerUtils();
    int counter;

    public Speed() {
        super("Speed", ModuleType.Movement);
        addValues(mode, timerSpeed, noAtkTimer);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        counter = 0;
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
        super.onDisable();
        timer.reset();
    }

    @EventTarget
    public void onMotion(EventUpdate e) {
        this.setSuffix(mode.getModeAsString());
        if (mode.getValue().equals(SMode.Hypixel)) {
            if (mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
                if (MoveUtils.isMoving()) {
                    MoveUtils.strafe(MoveUtils.getBaseMoveSpeed() * 1.6 + Math.random() / 100);
                    mc.thePlayer.motionY = 0.418;
                }
            }
            if (MoveUtils.isMoving()) {
                if (!ModuleManager.getModuleByClass(GameSpeed.class).isEnabled())
                    if ((!noAtkTimer.getValue() || Killaura.target == null)) {
                        mc.timer.timerSpeed = timerSpeed.getValue().floatValue();
                    }
                    else {
                        mc.timer.timerSpeed = 1f;
                    }
            }
            MoveUtils.strafe();
        }
        if (mode.getValue().equals(SMode.CSGO)) {
            if (mc.thePlayer.onGround && MoveUtils.isMoving()) {
                counter++;
                mc.thePlayer.motionY = 0.41999999999999999999999;
                MoveUtils.strafe(MoveUtils.getBaseMoveSpeed() + counter * 0.05);
            }
            if (!MoveUtils.isMoving() || mc.thePlayer.isCollidedHorizontally) {
                counter = 0;
            }
            MoveUtils.strafe();
        }
        if (mode.getValue().equals(SMode.Legit)) {
            if (mc.thePlayer.onGround && PlayerUtils.isMoving()) {
                mc.thePlayer.jump();
            }
        }
    }

    enum SMode {
        Hypixel, CSGO, Legit
    }
}