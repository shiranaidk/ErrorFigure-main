package errorfigure.module.modules.world;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.world.EventPacketRecieve;
import errorfigure.api.events.world.EventPacketSend;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.api.events.world.EventWorldLoad;
import errorfigure.api.value.Option;
import errorfigure.management.ModuleManager;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.module.modules.movement.Flight;
import errorfigure.module.modules.movement.Speed;
import errorfigure.ui.notification.Notification;
import errorfigure.ui.notification.NotificationManager;
import errorfigure.ui.notification.NotificationType;
import errorfigure.utils.*;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Disabler extends Module {
    public int counter = 0;
    public double posX;
    public double posY;
    public double posZ;
    public float yaw;
    public float pitch;
    private static Vec3 initPos;

    private ArrayList<Packet> packets = new ArrayList<>();
    boolean shouldActive, alrSendY;

    TimerUtils wdTimer = new TimerUtils();

    LinkedList<Packet> packetQueue = new LinkedList<>(), anotherQueue = new LinkedList<>();
    Option<Boolean> noC03s = new Option<>("No C03s", "No C03s", true),
    rotModify = new Option<>("RotationModify", "RotationModify", true)
    ;

    public Disabler() {
        super("Disabler", ModuleType.World);
        addValues(noC03s);
    }

    @EventTarget
    public void onWorldLoad(EventWorldLoad e) {
        packetQueue.clear();
        wdTimer.reset();
        alrSendY = false;
        shouldActive = false;
    };

    boolean isInventory(Short action) {
        return action > 0 && action < 100;
    }

    @Override
    public void onEnable() {
        packetQueue.clear();
        wdTimer.reset();
        counter = 0;
        alrSendY = false;
        shouldActive = false;
    }
    @EventTarget
    public void onMotion(EventUpdate event){
        setSuffix("Hypixel");
        if (!isHypixelLobby() && event.isPre()) {
            if (mc.isSingleplayer()) return;
            if (shouldActive && wdTimer.hasPassed(400L)) {
                while (!anotherQueue.isEmpty()) {
                    mc.getNetHandler().getNetworkManager().sendPacketNoEvent(anotherQueue.poll());
                }
                while (!packetQueue.isEmpty()) {
                    mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packetQueue.poll());
                }
            }
        }

    }
    @EventTarget
    private void onC04(EventPacketSend event) {
        if (mc.isSingleplayer()) return;

        if (!isHypixelLobby()) {
            if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
                event.setCancelled(true);
                packetQueue.add(event.getPacket());

                if (!shouldActive) {
                    shouldActive = true;
                    NotificationManager.show(new Notification(NotificationType.INFO, "Disabler working!", 3));
                }
            }
            if (event.getPacket() instanceof C00PacketKeepAlive) {
                event.setCancelled(true);
                anotherQueue.add(event.getPacket());

                wdTimer.reset();

            }
            if (event.getPacket() instanceof C03PacketPlayer || event.getPacket() instanceof C0BPacketEntityAction || event.getPacket() instanceof C08PacketPlayerBlockPlacement || event.getPacket() instanceof C0APacketAnimation) {
                if (!shouldActive)
                    event.setCancelled(true);
            }

        }

        if (noC03s.getValue() && event.getPacket() instanceof C03PacketPlayer)
        if (!(event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition) && !(event.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook) && !(event.getPacket() instanceof
        C03PacketPlayer.C06PacketPlayerPosLook))
        event.setCancelled(true);
    }
    @EventTarget
    public void onS08 (EventPacketRecieve event){
            if (event.getPacket() instanceof S08PacketPlayerPosLook && !shouldActive && !isHypixelLobby()) {
                if (alrSendY) {
                    //mc.netHandler.addToSendQueue(C06PacketPlayerPosLook(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch(), false))
                    event.setCancelled(true);
                } else {
                    alrSendY = true;
                }
            }
    }

    public static boolean isHypixelLobby() {
        if (mc.theWorld == null) return false;

        String target = "CLICK TO PLAY";
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity.getName().startsWith("§e§l")) {
                if (entity.getName().equals("§e§l" + target)) {
                    return true;
                }
            }
        }
        return false;
    }
}
