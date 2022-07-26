/*
 * Decompiled with CFR 0_132.
 */
package errorfigure.module.modules.world;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.world.EventPacketSend;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.utils.TimerUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import optifine.MathUtils;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PingSpoof
extends Module {
    private List<Packet> packetList = new CopyOnWriteArrayList<Packet>();
    private TimerUtils timer = new TimerUtils();

    public PingSpoof() {
        super("PingSpoof", ModuleType.World);
        this.setColor(new Color(117, 52, 203).getRGB());
    }

    @EventTarget
    private void onPacketSend(EventPacketSend e) {
        if (!mc.isSingleplayer()) {
            if (e.getPacket() instanceof C00PacketKeepAlive && this.mc.thePlayer.isEntityAlive()) {
                this.packetList.add(e.getPacket());
                e.setCancelled(true);
            }
            if (this.timer.hasReached(750.0)) {
                if (!this.packetList.isEmpty()) {
                    int i = 0;
                    double totalPackets = MathUtils.getIncremental(Math.random() * 10.0, 1.0);
                    for (Packet packet : this.packetList) {
                        if ((double)i >= totalPackets) continue;
                        ++i;
                        this.mc.getNetHandler().getNetworkManager().sendPacket(packet);
                        this.packetList.remove(packet);
                    }
                }
                this.mc.getNetHandler().getNetworkManager().sendPacket(new C00PacketKeepAlive(10000));
                this.timer.reset();
            }
        }
    }
}

