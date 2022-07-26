package errorfigure.module.modules.combat;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.player.EventAttack;
import errorfigure.api.events.world.EventPacketSend;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.api.value.Mode;
import errorfigure.api.value.Numbers;
import errorfigure.api.value.Option;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.utils.Helper;
import errorfigure.utils.TimerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Criticals extends Module {
    public static Mode mode = new Mode("Mode", "Mode", CritMode.values(), CritMode.NoGround);
    public static boolean Crit;
    private final TimerUtils prevent = new TimerUtils();
    public static Minecraft mc = Minecraft.getMinecraft();
    public TimerUtils timer = new TimerUtils();
    public Numbers<Double> Delay = new Numbers("Delay", "Delay", 200,0,1000,1);
    public Numbers<Double> hurttime = new Numbers("HurtTime", "HurtTime", 10,0,20,1);
    public Option<Boolean> debug = new Option<>("Debug", "Debug", false);
    private boolean attacked;
    boolean shouldLaunch;
    private int groundTicks;

    public Criticals() {
        super("Criticals", ModuleType.Combat);
        addValues(mode, hurttime, Delay);
    }

    private void crit (double yoffset, boolean onGround) {
        assert Minecraft.getMinecraft().getNetHandler() != null;
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                mc.thePlayer.posX,
                mc.thePlayer.posY + yoffset,
                mc.thePlayer.posZ,
                onGround
        ));
    }

    public void onEnable() {
        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
        attacked = false;
        shouldLaunch = false;
        prevent.reset();
        groundTicks = 0;
        timer.reset();
    }

    @EventTarget
    void onPacket(EventPacketSend e) {
        if (mode.getValue().equals(CritMode.NoGround)) {
            if (e.getPacket() instanceof C03PacketPlayer) {
                ((C03PacketPlayer) e.getPacket()).onGround = false;
            }
        }
    }

    @EventTarget
    public void onAttack(EventAttack e) {
        if (mc.thePlayer.onGround && !mc.thePlayer.isInWater()) {
            if (mode.getValue().equals(CritMode.Packet)) {
                crit(0.0625, false);
                crit(0, false);
                crit(1.1E-11, false);
                crit(0, false);
            }
            if (mode.getValue().equals(CritMode.Hypixel)) {
                crit(0.0320583200905231428, false);
                crit(0.00372301200301273833, false);
                crit(0.000372301197509012937, false);
                crit(Math.random() / 10000, false);
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        setSuffix(mode.getValue().equals(CritMode.Hypixel) ? "Edit" : mode.getModeAsString());
//        if (mode.getValue().equals(CritMode.Hypixel)) {
//            if (shouldLaunch) {
//                mc.thePlayer.motionY = 0.05;
//                shouldLaunch = false;
//             }
//        }
    }
    public enum CritMode {
        Packet, Hypixel, NoGround
    }
}