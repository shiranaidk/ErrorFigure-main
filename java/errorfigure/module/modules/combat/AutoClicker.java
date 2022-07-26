package errorfigure.module.modules.combat;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.world.EventTick;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.api.value.Numbers;
import errorfigure.api.value.Option;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.utils.TimeHelper;
import errorfigure.utils.math.MathUtil;
import net.minecraft.item.ItemSword;

public class AutoClicker extends Module {
    private TimeHelper timer = new TimeHelper();
    private TimeHelper blocktimer = new TimeHelper();
    public static Numbers<Float> mincps = new Numbers<>("Min CPS", "Min CPS", 8f, 1.0f, 20.0f, 1.0f);
    public static Numbers<Float> maxcps = new Numbers<>("Max CPS", "Max CPS", 12f, 1.0f, 20.0f, 1.0f);
    public static Option<Boolean> autoblock = new Option<>("AutoClicker", "Auto Block", false);

    private int delay;

    public AutoClicker() {
        super("AutoClicker", ModuleType.Combat);
        addValues(mincps, maxcps, autoblock);
    }

    @Override
    public void onEnable() {
        setDelay();
        super.onEnable();
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (event.isPre()) {
            if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
                return;
            }
            if (mc.playerController.getCurBlockDamageMP() != 0F) {
                return;
            }
            if (timer.delay(delay) && mc.gameSettings.keyBindAttack.pressed) {
                mc.gameSettings.keyBindAttack.pressed = false;
                // autoblock
                if (autoblock.getValue() && mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit.isEntityAlive()){
                    if (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword && blocktimer.delay(100)) {
                        mc.thePlayer.getCurrentEquippedItem().useItemRightClick(mc.theWorld, mc.thePlayer);
                        blocktimer.reset();
                    }
                }
                mc.setLeftClickCounter(0);
                mc.clickMouse();
                mc.gameSettings.keyBindAttack.pressed = true;
                setDelay();
                timer.reset();
            }
        }
    }

    @EventTarget
    public void onTick(EventTick event) {
        // 防止最小cps大于最大cps
        if (mincps.getValue() > maxcps.getValue()) {
            mincps.setValue(maxcps.getValue());
        }
    }

    private void setDelay()
    {
        delay = (int) MathUtil.getRandomFloat(1000.0F / mincps.getValue(), 1000.0F / maxcps.getValue());
    }
}
