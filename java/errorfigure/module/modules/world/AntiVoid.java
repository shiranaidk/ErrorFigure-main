package errorfigure.module.modules.world;

import errorfigure.api.events.world.EventPacketRecieve;
import errorfigure.api.events.world.EventPacketSend;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.api.value.Numbers;
import errorfigure.management.ModuleManager;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.module.modules.movement.Flight;
import errorfigure.utils.Helper;
import errorfigure.utils.MoveUtils;
import errorfigure.utils.TimeHelper;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import com.darkmagician6.eventapi.EventTarget;
import java.util.ArrayList;

public class AntiVoid extends Module {
    public double[] lastGroundPos = new double[3];
    public static Numbers<Float> pullbackTime = new Numbers<>("AntiVoid", "Pullback Time", 1500f, 1000f, 2000f, 100f);
    public static TimeHelper timer = new TimeHelper();
    public static ArrayList<C03PacketPlayer> packets = new ArrayList<>();

    public AntiVoid() {
        super("AntiVoid", ModuleType.Player);
    }

    public static boolean isInVoid() {
        for (int i = 0; i <= 128; i++) {
            if (MoveUtils.isOnGround(i)) {
                return false;
            }
        }
        return true;
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        setSuffix(mc.isSingleplayer() ? "SinglePlayer" : "Hypixel");
    }

    @EventTarget
    public void onPacket(EventPacketSend e) {
        if (!mc.isSingleplayer()) {
//            if (ModuleManager.getModuleByClass(Flight.class).isEnabled())
//                return;
//
//            if (!packets.isEmpty() && mc.thePlayer.ticksExisted < 100)
//                packets.clear();
//
//            if (e.getPacket() instanceof C03PacketPlayer) {
//                C03PacketPlayer packet = ((C03PacketPlayer) e.getPacket());
//                if (isInVoid()) {
//                    e.setCancelled(true);
//                    packets.add(packet);
//
//                    if (timer.isDelayComplete(pullbackTime.getValue())) {
//                        Helper.sendMessage("Send Packets");
//                        mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(lastGroundPos[0], lastGroundPos[1] - 1, lastGroundPos[2], true));
//                    }
//                } else {
//                    lastGroundPos[0] = mc.thePlayer.posX;
//                    lastGroundPos[1] = mc.thePlayer.posY;
//                    lastGroundPos[2] = mc.thePlayer.posZ;
//
//                    if (!packets.isEmpty()) {
//                        Helper.sendMessage("Release Packets - " + packets.size());
//                        for (C03PacketPlayer p : packets)
//                            mc.getNetHandler().getNetworkManager().sendPacketNoEvent(p);
//                        packets.clear();
//                    }
//                    timer.reset();
//                }
//            }
        }
    }

    @EventTarget
    public void onRevPacket(EventPacketRecieve e) {
        if (!mc.isSingleplayer()) {
//            if (e.getPacket() instanceof S08PacketPlayerPosLook && packets.size() > 1) {
//                Helper.sendMessage("Pullbacks Detected, clear packets list!");
//                packets.clear();
//            }
        }
    }

    public static boolean isPullbacking() {
        return ModuleManager.getModuleByClass(AntiVoid.class).isEnabled() && !packets.isEmpty();
    }
}
