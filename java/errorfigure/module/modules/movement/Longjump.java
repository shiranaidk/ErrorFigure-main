/*
 * Decompiled with CFR 0_132.
 */
package errorfigure.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.world.EventMove;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.api.value.Mode;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.ui.notification.Notification;
import errorfigure.ui.notification.NotificationManager;
import errorfigure.ui.notification.NotificationType;
import errorfigure.utils.MoveUtils;
import errorfigure.utils.math.MathUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

import java.awt.*;

public class Longjump
extends Module {
    private Mode<JumpMode> mode = new Mode("Mode", "mode", (Enum[])JumpMode.values(), (Enum)JumpMode.NCP);

    public Longjump() {
        super("LongJump", ModuleType.Movement);
        this.addValues(this.mode);
        this.setColor(new Color(76, 67, 216).getRGB());
    }

    public void damagePlayer(int damage) {
        if (damage < 1)
            damage = 1;
        if (damage > MathHelper.floor_double(mc.thePlayer.getMaxHealth()))
            damage = MathHelper.floor_double(mc.thePlayer.getMaxHealth());

        double offset = 0.0625;
        if (mc.thePlayer != null && mc.getNetHandler() != null && mc.thePlayer.onGround) {
            for (int i = 0; i <= ((3 + damage) / offset); i++) { // TODO: teach rederpz (and myself) how math works
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY, mc.thePlayer.posZ, (i == ((3 + damage) / offset))));
            }
        }
    }

    public void onEnable() {
//        if (mode.getValue().equals(JumpMode.NCP)) {
//            if (mc.thePlayer.onGround) {
//                damagePlayer(4);
//                mc.thePlayer.setSpeed(MoveUtils.getBaseMoveSpeed() * 1.5);
//                mc.thePlayer.motionY = 0.4;
//            }
//            else {
//                setEnabled(false);
//            }
//        }
        setEnabled(false);
        NotificationManager.show(new Notification(NotificationType.WARNING, "Patched", 3));
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
//        setSuffix(mode.getModeAsString());
//        if (mode.getValue().equals(JumpMode.NCP)) {
//            mc.thePlayer.motionY += 0.022;
//            if (mc.thePlayer.onGround) {
//                setEnabled(false);
//            }
//        }
    }


    static enum JumpMode {
        NCP
    }

}

