package errorfigure.module.modules.movement;


import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.world.EventPacketRecieve;
import errorfigure.api.events.world.EventPacketSend;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.api.value.Mode;
import errorfigure.management.ModuleManager;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.module.modules.combat.Killaura;
import errorfigure.utils.Helper;
import errorfigure.utils.PacketUtils;
import errorfigure.utils.TimerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.*;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.util.BlockPos;

public class NoSlow extends Module {

    private Mode<Enum> mode = new Mode("Mode", "Mode",  JMode.values(),  JMode.Hypixel);
    public TimerUtils timer = new TimerUtils();

    boolean nextTemp, lastBlockingStat, waitC03;
    List<Packet> packetBuf = new LinkedList<>();

    public NoSlow() {
        super("NoSlow", ModuleType.Movement);
        this.setColor(new Color(188, 233, 248).getRGB());
        this.addValues(this.mode);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventTarget
    void onUpdate(EventUpdate e) {
        setSuffix(mode.getModeAsString());
        if (mode.getValue().equals(JMode.Hypixel)) {
            if ((e.isPre() && mc.thePlayer.getItemInUse() != null && mc.thePlayer.getItemInUse().getItem() != null)) {
                if (mc.thePlayer.isUsingItem() && mc.thePlayer.getItemInUseCount() >= 1) {
                    Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
            }
        }
    }
    enum JMode {
        Vanilla, Hypixel
    }
}